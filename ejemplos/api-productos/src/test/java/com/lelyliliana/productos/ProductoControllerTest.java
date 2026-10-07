package com.lelyliliana.productos;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import java.math.BigDecimal;import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(ProductoController.class)
@Import(SecurityConfig.class)
@org.springframework.test.context.TestPropertySource(properties={"app.editor-password=prueba-editor","app.lector-password=prueba-lector"})
class ProductoControllerTest {
 @Autowired MockMvc mvc;@MockitoBean ProductoService service;
 @Test void listaPaginada()throws Exception{when(service.listar("",0,20)).thenReturn(new Pagina<>(List.of(new ProductoResponse(1L,"TEC-1","Teclado",new BigDecimal("100"),1L,"Tecnología",0L,true)),0,20,1,1));mvc.perform(get("/api/productos")).andExpect(status().isOk()).andExpect(jsonPath("$.contenido[0].nombre").value("Teclado")).andExpect(jsonPath("$.total").value(1));}
 @Test void noEncontrado()throws Exception{when(service.buscar(99L)).thenThrow(new ProductoNoEncontrado(99L));mvc.perform(get("/api/productos/99")).andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value("NOT_FOUND"));}
 @Test void postInvalido()throws Exception{mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).with(csrf()).contentType("application/json").content("{\"sku\":\"bad\",\"nombre\":\"\",\"precio\":0,\"categoriaId\":1}")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.precio").exists());verify(service,never()).crear(any());}
 @Test void creacionContrato()throws Exception{when(service.crear(any())).thenReturn(new ProductoResponse(5L,"LIB-3","Libro",new BigDecimal("10"),1L,"Libros",0L,true));mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).with(csrf()).contentType("application/json").content("{\"sku\":\"LIB-3\",\"nombre\":\"Libro\",\"precio\":10,\"categoriaId\":1}")).andExpect(status().isCreated()).andExpect(header().string("Location","/api/productos/5")).andExpect(jsonPath("$.version").value(0));}
 @Test void conflicto()throws Exception{when(service.crear(any())).thenThrow(new Conflicto("Duplicado"));mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).with(csrf()).contentType("application/json").content("{\"sku\":\"LIB-3\",\"nombre\":\"Libro\",\"precio\":10,\"categoriaId\":1}")).andExpect(status().isConflict()).andExpect(jsonPath("$.code").value("CONFLICT"));}
 @Test void anonimoConCsrf401()throws Exception{mvc.perform(post("/api/productos").with(csrf()).contentType("application/json").content("{}")).andExpect(status().isUnauthorized()).andExpect(header().exists("WWW-Authenticate"));}
 @Test void lector403()throws Exception{mvc.perform(post("/api/productos").with(user("lector").authorities(()->"PRODUCT_READ")).with(csrf()).contentType("application/json").content("{}")).andExpect(status().isForbidden());}
 @Test void editorSinCsrf403()throws Exception{mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).contentType("application/json").content("{}")).andExpect(status().isForbidden());}
 @Test void jsonMalformado400()throws Exception{mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).with(csrf()).contentType("application/json").content("{" )).andExpect(status().isBadRequest());}
}
