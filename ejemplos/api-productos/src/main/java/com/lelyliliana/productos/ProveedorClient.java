package com.lelyliliana.productos;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import java.net.http.HttpClient;
@Component
class ProveedorClient {
 private final RestClient client;private final int intentos;
 ProveedorClient(AppProperties p){
  var factory=new JdkClientHttpRequestFactory(HttpClient.newBuilder().connectTimeout(p.connectTimeout()).build());factory.setReadTimeout(p.readTimeout());
  client=RestClient.builder().baseUrl(p.proveedorUrl().toString()).requestFactory(factory).build();intentos=p.intentos();
 }
 Cotizacion buscar(String sku){
  if(sku==null || !sku.matches("[A-Z0-9-]{1,32}"))throw new IllegalArgumentException("SKU inválido");
  for(int i=1;i<=intentos;i++){
   try{
    var c=client.get().uri("/cotizaciones/{sku}",sku).retrieve().body(Cotizacion.class);
    if(c==null || !sku.equals(c.sku()) || c.precio()==null || c.precio().signum()<=0)throw new ProveedorNoDisponible();
    return c;
   }catch(HttpClientErrorException.NotFound e){throw new NoEncontrado("Cotización no encontrada");}
   catch(RestClientResponseException e){
    int code=e.getStatusCode().value();
    if(i<intentos && (code==502 || code==503 || code==504))continue;
    throw new ProveedorNoDisponible();
   }catch(RestClientException e){throw new ProveedorNoDisponible();}
  }
  throw new ProveedorNoDisponible();
 }
}
