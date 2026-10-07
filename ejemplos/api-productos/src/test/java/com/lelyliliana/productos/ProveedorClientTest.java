package com.lelyliliana.productos;
import com.sun.net.httpserver.HttpServer;
import java.net.*;import java.time.Duration;import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.*;import static org.junit.jupiter.api.Assertions.*;
class ProveedorClientTest {
 HttpServer server;AtomicInteger llamadas=new AtomicInteger();int status=200;String body="{\"sku\":\"LIB-01\",\"precio\":75}";long pausa=0;boolean transitorio=false;
 @BeforeEach void iniciar()throws Exception{server=HttpServer.create(new InetSocketAddress("127.0.0.1",0),0);server.createContext("/cotizaciones",ex->{int n=llamadas.incrementAndGet();try{Thread.sleep(pausa);int code=transitorio&&n==1?503:status;byte[] bytes=body.getBytes(java.nio.charset.StandardCharsets.UTF_8);ex.getResponseHeaders().set("Content-Type","application/json");ex.sendResponseHeaders(code,bytes.length);ex.getResponseBody().write(bytes);}catch(Exception ignored){}finally{ex.close();}});server.start();}
 @AfterEach void cerrar(){server.stop(0);}
 ProveedorClient client(int intentos){return new ProveedorClient(new AppProperties("test","editor-test","lector-test",URI.create("http://127.0.0.1:"+server.getAddress().getPort()),Duration.ofMillis(100),Duration.ofMillis(100),intentos));}
 @Test void contratoValido(){assertEquals("LIB-01",client(1).buscar("LIB-01").sku());assertEquals(1,llamadas.get());}
 @Test void noEncontrado(){status=404;assertThrows(NoEncontrado.class,()->client(2).buscar("LIB-01"));assertEquals(1,llamadas.get());}
 @Test void rateLimitNoReintenta(){status=429;assertThrows(ProveedorNoDisponible.class,()->client(2).buscar("LIB-01"));assertEquals(1,llamadas.get());}
 @Test void transitorioAcotado(){transitorio=true;assertEquals("LIB-01",client(2).buscar("LIB-01").sku());assertEquals(2,llamadas.get());}
 @Test void falloPermanente(){status=503;assertThrows(ProveedorNoDisponible.class,()->client(2).buscar("LIB-01"));assertEquals(2,llamadas.get());}
 @Test void respuestaInvalida(){body="{\"sku\":\"OTRO\",\"precio\":-1}";assertThrows(ProveedorNoDisponible.class,()->client(1).buscar("LIB-01"));}
 @Test void jsonInvalido(){body="texto";assertThrows(ProveedorNoDisponible.class,()->client(1).buscar("LIB-01"));}
 @Test void timeoutAcotado(){pausa=350;assertThrows(ProveedorNoDisponible.class,()->client(1).buscar("LIB-01"));}
 @Test void entradaNoConstruyeURLArbitraria(){assertThrows(IllegalArgumentException.class,()->client(1).buscar("http://otro"));assertEquals(0,llamadas.get());}
}
