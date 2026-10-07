"""Verifica documentación, pruebas Maven y el contrato del JAR en bases aisladas."""
from pathlib import Path
import argparse, contextlib, json, os, re, secrets, shutil, socket, subprocess, sys, time, uuid
import xml.etree.ElementTree as ET
from concurrent.futures import ThreadPoolExecutor
from peticion import cliente, enviar
ROOT=Path(__file__).resolve().parents[1]
OUT=ROOT/'salidas';OUT.mkdir(exist_ok=True)
checks=0

def comprobar(ok, mensaje):
    global checks
    if not ok:raise AssertionError(mensaje)
    checks+=1

def puerto():
    with socket.socket() as s:s.bind(('127.0.0.1',0));return s.getsockname()[1]

def documentos():
    unidades=sorted(ROOT.glob('unidad[0-9][0-9]-*'))
    comprobar(len(unidades)==31,'31 unidades')
    for unit in unidades:
        for name in ('README.md','LABORATORIO.md','PRACTICA.md','SOLUCIONES.md'):
            comprobar((unit/name).is_file(),str(unit/name))
    for file in ROOT.rglob('*.md'):
        if any(x in file.parts for x in ('target','salidas','.git')):continue
        for target in re.findall(r'\]\(([^)]+)\)',file.read_text(encoding='utf-8')):
            if '://' in target or target.startswith('#'):continue
            target=target.split('#')[0]
            comprobar((file.parent/target).exists(),f'Enlace inexistente: {file.relative_to(ROOT)} -> {target}')

def pruebas():
    total=0
    for module in ('laboratorios/fundamentos','ejemplos/api-productos'):
        reports=list((ROOT/module/'target/surefire-reports').glob('TEST-*.xml'))
        comprobar(bool(reports),'Faltan informes Maven '+module)
        for report in reports:
            data=ET.parse(report).getroot().attrib
            comprobar(int(data.get('failures',0))+int(data.get('errors',0))+int(data.get('skipped',0))==0,report.name)
            total+=int(data['tests'])
    comprobar(total>=52,'Se esperan al menos 52 casos Maven')
    return total

@contextlib.contextmanager
def aplicacion(module,env,label,args=(),docker=False):
    port=puerto();settings=os.environ.copy();settings.update(env)
    settings.update(SERVER_PORT=str(port),SERVER_ADDRESS='127.0.0.1')
    name='curso-spring-'+uuid.uuid4().hex[:10]
    if docker:
        keys=list(env)+['SERVER_PORT','SERVER_ADDRESS']
        command=['docker','run','--rm','--name',name,'--network','host']
        for key in keys:command+=['-e',key]
        command+=['curso-spring-check']
    else:
        jar='fundamentos' if module.startswith('laboratorios') else 'api-productos'
        command=['java','-jar',str(ROOT/module/'target'/f'{jar}.jar'),*args]
    with (OUT/f'{label}.log').open('wb') as log:
        process=subprocess.Popen(command,env=settings,stdout=log,stderr=subprocess.STDOUT)
        base=f'http://127.0.0.1:{port}'
        try:
            ready='/api/saludos' if module.startswith('laboratorios') else '/actuator/health'
            for _ in range(180):
                if process.poll() is not None:raise RuntimeError(f'{label} terminó; consulta salidas/{label}.log')
                try:
                    if enviar(*cliente(),base+ready)[0]==200:break
                except OSError:pass
                time.sleep(.5)
            else:raise RuntimeError('Tiempo de arranque agotado: '+label)
            yield base
        finally:
            if docker:subprocess.run(['docker','stop',name],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL)
            if process.poll() is None:
                process.terminate()
                try:process.wait(timeout=20)
                except subprocess.TimeoutExpired:process.kill();process.wait()

def contrato(base,editor,lector,category,expected):
    anonymous=cliente();writer=cliente('editor',editor);reader=cliente('lector',lector)
    def call(session,path,method='GET',body=None,expected_status=200,csrf=True):
        status,headers,result=enviar(*session,base+path,method,body,csrf)
        comprobar(status==expected_status,f'{method} {path}: HTTP {status}, esperado {expected_status}: {result}')
        return headers,result
    _,page=call(anonymous,'/api/productos?pagina=0&tamano=2');comprobar(page['total']==expected,'Cantidad inicial')
    call(anonymous,'/api/productos?tamano=101',expected_status=400)
    payload={'sku':'CHECK-'+uuid.uuid4().hex[:8].upper(),'nombre':'Producto de comprobación','precio':25.5,'categoriaId':category}
    call(anonymous,'/api/productos','POST',payload,401)
    call(reader,'/api/productos','POST',payload,403)
    call(writer,'/api/productos','POST',payload,403,False)
    headers,item=call(writer,'/api/productos','POST',payload,201)
    path='/api/productos/'+str(item['id']);comprobar(headers['Location'].endswith(path),'Location de creación')
    call(writer,'/api/productos','POST',payload,409)
    call(writer,'/api/productos','POST',{**payload,'sku':'INV','precio':0},400)
    call(writer,'/api/productos','POST',{**payload,'sku':'CAT-MISSING','categoriaId':999999999},404)
    _,stored=call(anonymous,path);comprobar(stored['version']==0 and stored['precio']==25.5,'Producto persistido')
    update={**payload,'precio':30,'version':0}
    _,changed=call(writer,path,'PUT',update);comprobar(changed['version']==1,'Versión aumentada')
    call(writer,path,'PUT',update,409)
    _,stored=call(anonymous,path);comprobar(stored['precio']==30,'Conflicto no cambia precio')
    call(writer,path,'DELETE',expected_status=204);call(anonymous,path,expected_status=404)
    call(anonymous,'/actuator/metrics',expected_status=401);call(writer,'/actuator/metrics')
    _,health=call(anonymous,'/actuator/health');comprobar('components' not in health,'Sin detalles públicos')
    call(anonymous,'/actuator/health/readiness');call(anonymous,'/actuator/health/liveness')
    request=cliente();request[1]['X-Request-Id']='check-123'
    headers,_=call(request,'/api/productos');comprobar(headers['X-Request-Id']=='check-123','Identificador de petición')
    call(anonymous,'/api/cotizaciones/CHECK',expected_status=503)
    return writer

def base_env():
    return {'APP_EDITOR_PASSWORD':secrets.token_urlsafe(24),'APP_LECTOR_PASSWORD':secrets.token_urlsafe(24),'PROVEEDOR_URL':f'http://127.0.0.1:{puerto()}'}

def postgres(docker=False):
    import psycopg
    from psycopg import sql
    host=os.environ.get('PGHOST','127.0.0.1');port=os.environ.get('PGPORT','5432')
    comprobar(host in ('localhost','127.0.0.1','::1'),'PostgreSQL de práctica local')
    suffix=uuid.uuid4().hex[:12];db='curso_check_'+suffix;role=db;password=secrets.token_urlsafe(24)
    admin=psycopg.connect(dbname='postgres',autocommit=True)
    created_role=False;created_db=False
    try:
        admin.execute(sql.SQL('CREATE ROLE {} LOGIN PASSWORD {}').format(sql.Identifier(role),sql.Literal(password)));created_role=True
        admin.execute(sql.SQL('CREATE DATABASE {} OWNER {}').format(sql.Identifier(db),sql.Identifier(role)));created_db=True
        env=base_env();env.update(SPRING_PROFILES_ACTIVE='prod',DB_URL=f'jdbc:postgresql://{host}:{port}/{db}',DB_USER=role,DB_PASSWORD=password)
        with aplicacion('ejemplos/api-productos',env,'postgres-docker' if docker else 'postgres',docker=docker) as base:
            writer=cliente('editor',env['APP_EDITOR_PASSWORD'])
            status,_,cat=enviar(*writer,base+'/api/categorias','POST',{'nombre':'Pruebas'});comprobar(status==201,'Crear categoría en base vacía')
            contrato(base,env['APP_EDITOR_PASSWORD'],env['APP_LECTOR_PASSWORD'],cat['id'],0)
            with psycopg.connect(host=host,port=port,dbname=db,user=role,password=password) as connection:
                versions=connection.execute('SELECT version FROM flyway_schema_history WHERE success ORDER BY installed_rank').fetchall()
                comprobar(versions==[('1',),('2',)],'Solo migraciones V1 y V2 en prod')
                comprobar(connection.execute('SELECT count(*) FROM producto').fetchone()[0]==0,'Eliminación persistida en PostgreSQL')
            payload={'sku':'RACE-01','nombre':'Carrera','precio':10,'categoriaId':cat['id']}
            def crear(_):return enviar(*cliente('editor',env['APP_EDITOR_PASSWORD']),base+'/api/productos','POST',payload)[0]
            with ThreadPoolExecutor(max_workers=2) as pool:statuses=sorted(pool.map(crear,range(2)))
            comprobar(statuses==[201,409],'UNIQUE protege creación concurrente')
    finally:
        if created_db:admin.execute(sql.SQL('DROP DATABASE {} WITH (FORCE)').format(sql.Identifier(db)))
        if created_role:admin.execute(sql.SQL('DROP ROLE {}').format(sql.Identifier(role)))
        admin.close()

if __name__=='__main__':
    parser=argparse.ArgumentParser();parser.add_argument('--skip-build',action='store_true');parser.add_argument('--postgres',action='store_true');parser.add_argument('--docker',action='store_true');args=parser.parse_args()
    documentos()
    if not args.skip_build:
        mvn=shutil.which('mvn.cmd' if os.name=='nt' else 'mvn')
        if not mvn:parser.error('Instala Maven y comprueba PATH')
        for module in ('laboratorios/fundamentos','ejemplos/api-productos'):subprocess.run([mvn,'-B','-f',str(ROOT/module/'pom.xml'),'verify'],check=True)
    tests=pruebas()
    with aplicacion('laboratorios/fundamentos',{},'fundamentos',('--saludo.prefijo=Bienvenida',)) as base:
        status,_,data=enviar(*cliente(),base+'/api/saludos?nombre=Leli')
        comprobar(status==200 and data['mensaje']=='Bienvenida, Leli','Configuración externa del JAR')
    env=base_env()
    with aplicacion('ejemplos/api-productos',env,'h2') as base:contrato(base,env['APP_EDITOR_PASSWORD'],env['APP_LECTOR_PASSWORD'],1,4)
    if args.postgres:postgres()
    if args.docker:
        if sys.platform!='linux':parser.error('--docker utiliza red host en Linux; usa Compose para práctica en otros sistemas')
        postgres(docker=True)
    result={'pruebas_maven':tests,'comprobaciones':checks,'postgres':args.postgres,'docker':args.docker}
    (OUT/'resultado.json').write_text(json.dumps(result,indent=2),encoding='utf-8');print(json.dumps(result))
