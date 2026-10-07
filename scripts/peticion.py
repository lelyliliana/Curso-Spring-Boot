"""Cliente de práctica local: mantiene cookie y obtiene CSRF para escrituras."""
import argparse,base64,getpass,http.cookiejar,json,os,urllib.request,urllib.error,urllib.parse

def cliente(usuario=None,clave=None):
    opener=urllib.request.build_opener(urllib.request.ProxyHandler({}),urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()))
    headers={}
    if usuario:
        headers['Authorization']='Basic '+base64.b64encode(f'{usuario}:{clave}'.encode()).decode()
    return opener,headers

def enviar(opener,headers,url,metodo='GET',cuerpo=None,csrf=True):
    h=dict(headers)
    if metodo not in ('GET','HEAD','OPTIONS') and csrf:
        base=urllib.parse.urlsplit(url)
        token_url=urllib.parse.urlunsplit((base.scheme,base.netloc,'/api/csrf','',''))
        with opener.open(urllib.request.Request(token_url,headers=h),timeout=10) as response:
            token=json.load(response)
        h[token['header']]=token['token']
    data=None if cuerpo is None else json.dumps(cuerpo,ensure_ascii=False).encode('utf-8')
    if data is not None:h['Content-Type']='application/json'
    request=urllib.request.Request(url,data=data,headers=h,method=metodo)
    try:response=opener.open(request,timeout=15)
    except urllib.error.HTTPError as error:response=error
    with response:
        raw=response.read().decode('utf-8')
        return response.status,dict(response.headers),json.loads(raw) if raw else None

if __name__=='__main__':
    p=argparse.ArgumentParser();p.add_argument('metodo');p.add_argument('url');p.add_argument('--json');p.add_argument('--usuario');args=p.parse_args()
    target=urllib.parse.urlsplit(args.url)
    if target.scheme!='http' or target.hostname not in ('localhost','127.0.0.1','::1'):p.error('Este cliente de práctica usa HTTP local')
    payload=None
    if args.json:
        with open(args.json,encoding='utf-8') as f:payload=json.load(f)
    clave=getpass.getpass('Contraseña local: ') if args.usuario else None
    status,headers,body=enviar(*cliente(args.usuario,clave),args.url,args.metodo.upper(),payload)
    print('HTTP',status)
    for key in ('Content-Type','Location','X-Request-Id'):
        if key in headers:print(key+':',headers[key])
    if body is not None:print(json.dumps(body,ensure_ascii=False,indent=2))
