package com.lelyliliana.productos;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cotizaciones")
class CotizacionController {
 private final ProveedorClient client;
 CotizacionController(ProveedorClient client){this.client=client;}
 @GetMapping("/{sku}") Cotizacion buscar(@PathVariable String sku){return client.buscar(sku);}
}
