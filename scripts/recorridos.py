"""Doce recorridos guiados sobre JAR locales aislados, con datos ficticios."""
import argparse
import json
import re
from pathlib import Path
from peticion import cliente, enviar
from verificar import aplicacion, base_env

ROOT = Path(__file__).resolve().parents[1]
MODULOS = {
    'fundamentos': ('laboratorios/fundamentos', 'fundamentos'),
    'rest': ('laboratorios/rest-memoria', 'rest-memoria'),
    'jpa': ('laboratorios/jpa-productos', 'jpa-productos'),
    'integrado': ('ejemplos/api-productos', 'api-productos'),
}


class Recorrido:
    def __init__(self, base, csrf=False):
        self.base = base
        self.csrf = csrf
        self.casos = 0
        self.peticiones = 0
        self.comprobaciones = 0

    def caso(self, titulo):
        self.casos += 1
        print('\nCaso: ' + titulo, flush=True)

    def comprobar(self, condicion, mensaje):
        if not condicion:
            raise AssertionError(mensaje)
        self.comprobaciones += 1

    def pedir(self, sesion, ruta, metodo='GET', cuerpo=None, status=200, csrf=None):
        real, headers, datos = enviar(*sesion, self.base + ruta, metodo, cuerpo,
                                     self.csrf if csrf is None else csrf)
        self.peticiones += 1
        print(f'  {metodo} {ruta} -> HTTP {real} (esperado {status})', flush=True)
        self.comprobar(real == status, f'{metodo} {ruta}: esperado {status}, recibido {real}')
        return headers, datos


def fundamentos(r, _env):
    anonimo = cliente()
    r.caso('1. Configuración externa y respuesta del servicio')
    _, saludo = r.pedir(anonimo, '/api/saludos?nombre=Leli')
    r.comprobar(saludo['mensaje'] == 'Buen día, Leli', 'Prefijo externo')
    r.comprobar(re.fullmatch(r'\d{4}-\d{2}-\d{2}', saludo['fecha']) is not None, 'Fecha ISO')
    print('  El mensaje usa el prefijo externo; la fecha procede del Clock del servicio.')
    r.caso('2. Nombre inválido sin resultado exitoso')
    r.pedir(anonimo, '/api/saludos?nombre=' + 'x' * 41, status=400)
    r.pedir(anonimo, '/api/saludos?nombre=%20%20', status=400)


def rest(r, _env):
    anonimo = cliente()
    base = {'sku': 'GUIA-BASE', 'nombre': 'Bases de datos', 'precio': 15}
    r.caso('3. Creación con Location y contrato sin campos JPA')
    headers, producto = r.pedir(anonimo, '/api/productos', 'POST', base, 201)
    ruta = '/api/productos/' + str(producto['id'])
    r.comprobar(headers['Location'].endswith(ruta), 'Location coincide con id')
    r.comprobar(headers['Content-Type'].startswith('application/json'), 'Respuesta JSON')
    r.comprobar(set(producto) == {'id', 'sku', 'nombre', 'precio'}, 'DTO de etapa REST')
    r.pedir(anonimo, ruta)
    r.caso('4. Filtro, total y página vacía')
    r.pedir(anonimo, '/api/productos', 'POST',
            {'sku': 'GUIA-ALG', 'nombre': 'Algoritmos', 'precio': 20}, 201)
    _, pagina = r.pedir(anonimo, '/api/productos?nombre=base&pagina=0&tamano=2')
    r.comprobar(pagina['total'] == 1 and len(pagina['contenido']) == 1, 'Una coincidencia')
    r.comprobar(pagina['contenido'][0]['sku'] == 'GUIA-BASE', 'SKU creado por el recorrido')
    _, vacia = r.pedir(anonimo, '/api/productos?nombre=base&pagina=1&tamano=2')
    r.comprobar(vacia['contenido'] == [] and vacia['total'] == 1, 'Página vacía conserva total')
    r.caso('5. Forma inválida, duplicado y ausencia tienen causas diferentes')
    _, error = r.pedir(anonimo, '/api/productos', 'POST', {**base, 'precio': 1.001}, 400)
    r.comprobar(error['code'] == 'VALIDATION_ERROR', 'Precio inválido se rechaza en validación')
    r.pedir(anonimo, '/api/productos', 'POST', base, 409)
    r.pedir(anonimo, '/api/productos/999999999', status=404)
    _, total = r.pedir(anonimo, '/api/productos')
    r.comprobar(total['total'] == 2, 'Rechazos no crean registros')


def jpa(r, _env):
    anonimo = cliente()
    base = {'sku': 'GUIA-JPA', 'nombre': 'Libro de versión', 'precio': 10, 'categoriaId': 1}
    r.caso('6. Actualización válida y versión antigua sin sobrescritura')
    _, producto = r.pedir(anonimo, '/api/productos', 'POST', base, 201)
    ruta = '/api/productos/' + str(producto['id'])
    r.comprobar(producto['version'] == 0, 'Versión inicial')
    _, nuevo = r.pedir(anonimo, ruta, 'PUT', {**base, 'precio': 15, 'version': 0})
    r.comprobar(nuevo['version'] == 1, 'Versión incrementada')
    r.pedir(anonimo, ruta, 'PUT', {**base, 'precio': 99, 'version': 0}, 409)
    _, guardado = r.pedir(anonimo, ruta)
    r.comprobar(guardado['precio'] == 15 and guardado['version'] == 1, 'Conflicto conserva dato')
    r.caso('7. Referencia válida en forma, ausente en la base')
    r.pedir(anonimo, '/api/productos', 'POST',
            {**base, 'sku': 'GUIA-SIN-CAT', 'categoriaId': 999999999}, 404)
    r.pedir(anonimo, '/api/productos?tamano=101', status=400)


def integrado(r, env):
    anonimo = cliente()
    editor = cliente('editor', env['APP_EDITOR_PASSWORD'])
    lector = cliente('lector', env['APP_LECTOR_PASSWORD'])
    base = {'sku': 'GUIA-SEG', 'nombre': 'Producto de permisos', 'precio': 10, 'categoriaId': 1}
    r.caso('8. Matriz de identidad, permiso y CSRF')
    r.pedir(anonimo, '/api/productos', 'POST', base, 401)
    r.pedir(lector, '/api/productos', 'POST', base, 403)
    r.pedir(editor, '/api/productos', 'POST', base, 403, csrf=False)
    _, producto = r.pedir(editor, '/api/productos', 'POST', base, 201)
    r.caso('9. Identificador de petición válido y reemplazo del inválido')
    trazada = cliente()
    trazada[1]['X-Request-Id'] = 'guia-123'
    headers, _ = r.pedir(trazada, '/api/productos')
    r.comprobar(headers['X-Request-Id'] == 'guia-123', 'Correlación válida')
    trazada[1]['X-Request-Id'] = 'x' * 80
    headers, _ = r.pedir(trazada, '/api/productos')
    r.comprobar(re.fullmatch(r'[a-f0-9-]{36}', headers['X-Request-Id']) is not None,
                'Identificador inválido reemplazado')
    r.caso('10. Salud pública y métricas protegidas')
    _, salud = r.pedir(anonimo, '/actuator/health')
    r.comprobar(salud['status'] == 'UP' and 'components' not in salud, 'Salud pública acotada')
    r.pedir(anonimo, '/actuator/metrics', status=401)
    r.pedir(lector, '/actuator/metrics', status=403)
    r.pedir(editor, '/actuator/metrics')
    r.caso('11. Captura de precio, historial y transición rechazada')
    _, pedido = r.pedir(editor, '/api/pedidos', 'POST',
                        {'detalles': [{'productoId': producto['id'], 'cantidad': 2}]}, 201)
    ruta = '/api/pedidos/' + str(pedido['id'])
    producto_ruta = '/api/productos/' + str(producto['id'])
    r.comprobar(pedido['detalles'][0]['precioUnitario'] is None, 'Borrador sin precio histórico')
    _, actualizado = r.pedir(editor, producto_ruta, 'PUT',
                             {**base, 'precio': 15, 'version': producto['version']})
    _, confirmado = r.pedir(editor, ruta + '/confirmacion', 'POST', {'version': pedido['version']})
    r.comprobar(confirmado['total'] == 30, 'Captura 15 por dos unidades al confirmar')
    r.pedir(editor, producto_ruta, 'PUT', {**base, 'precio': 99, 'version': actualizado['version']})
    _, historico = r.pedir(editor, ruta)
    r.comprobar(historico['total'] == 30, 'Catálogo posterior no altera historial')
    r.pedir(editor, producto_ruta, 'DELETE', status=409)
    _, cancelado = r.pedir(editor, ruta + '/cancelacion', 'POST', {'version': historico['version']})
    r.comprobar(cancelado['estado'] == 'CANCELADO' and cancelado['total'] == 30,
                'Cancelación conserva precio histórico')
    r.pedir(editor, ruta + '/confirmacion', 'POST', {'version': cancelado['version']}, 409)
    r.caso('12. Proveedor no disponible sin convertirlo en éxito')
    _, fallo = r.pedir(anonimo, '/api/cotizaciones/GUIA-SEG', status=503)
    r.comprobar(fallo['code'] == 'PROVIDER_UNAVAILABLE' and 'trace' not in fallo,
                'Fallo externo representado sin traza interna')


FUNCIONES = {'fundamentos': fundamentos, 'rest': rest, 'jpa': jpa, 'integrado': integrado}


def recorrer(etapa='fundamentos'):
    etapas = list(MODULOS) if etapa == 'todas' else [etapa]
    for nombre in etapas:
        modulo, jar = MODULOS[nombre]
        if not (ROOT / modulo / 'target' / (jar + '.jar')).is_file():
            raise RuntimeError(f'Falta el JAR de {nombre}. Ejecuta mvn -f {modulo}/pom.xml verify primero.')
    resultado = {'etapas': etapas, 'casos': 0, 'peticiones': 0, 'comprobaciones': 0}
    for nombre in etapas:
        modulo, _ = MODULOS[nombre]
        env = base_env() if nombre == 'integrado' else {}
        if nombre in ('jpa', 'integrado'):
            env['SPRING_PROFILES_ACTIVE'] = 'dev'
        args = ('--saludo.prefijo=Buen día',) if nombre == 'fundamentos' else ()
        print('\nEtapa: ' + nombre, flush=True)
        with aplicacion(modulo, env, 'recorrido-' + nombre, args, aislar=True) as base:
            recorrido = Recorrido(base, csrf=(nombre == 'integrado'))
            FUNCIONES[nombre](recorrido, env)
            for key in ('casos', 'peticiones', 'comprobaciones'):
                resultado[key] += getattr(recorrido, key)
    print('\n' + json.dumps(resultado, ensure_ascii=False), flush=True)
    return resultado


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--etapa', choices=[*MODULOS, 'todas'], default='fundamentos')
    recorrer(parser.parse_args().etapa)
