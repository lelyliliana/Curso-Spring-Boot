"""Medición acotada de lecturas sobre un servidor local de práctica."""
import argparse,concurrent.futures,time,statistics,urllib.request,urllib.parse,json
if __name__=='__main__':
 p=argparse.ArgumentParser();p.add_argument('--url',default='http://localhost:8080/api/productos');p.add_argument('--solicitudes',type=int,default=100);p.add_argument('--concurrencia',type=int,default=4);a=p.parse_args()
 if urllib.parse.urlsplit(a.url).hostname not in ('localhost','127.0.0.1','::1') or not 1<=a.solicitudes<=1000 or not 1<=a.concurrencia<=16:p.error('Usa un servidor local, 1..1000 solicitudes y 1..16 clientes')
 def once(_):
  start=time.perf_counter()
  try:
   with urllib.request.urlopen(a.url,timeout=10) as r:r.read();ok=r.status==200
  except Exception:ok=False
  return (time.perf_counter()-start)*1000,ok
 for _ in range(5):once(0)
 start=time.perf_counter()
 with concurrent.futures.ThreadPoolExecutor(max_workers=a.concurrencia) as pool:result=list(pool.map(once,range(a.solicitudes)))
 duration=time.perf_counter()-start;values=sorted(x[0] for x in result)
 print(json.dumps({'solicitudes':len(result),'errores':sum(not x[1] for x in result),'duracion_s':round(duration,3),'solicitudes_por_s':round(len(result)/duration,2),'promedio_ms':round(statistics.mean(values),2),'p95_ms':round(values[max(0,__import__('math').ceil(.95*len(values))-1)],2)},indent=2))
