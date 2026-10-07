package com.lelyliliana.productos;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
class CsrfController {
 @GetMapping("/api/csrf") Map<String,String> token(CsrfToken t){return Map.of("header",t.getHeaderName(),"token",t.getToken());}
}
