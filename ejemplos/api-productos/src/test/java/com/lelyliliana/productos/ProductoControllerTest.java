package com.lelyliliana.productos;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {
 @Autowired MockMvc mvc;
 @MockitoBean ProductoService service;
 @Test void lista() throws Exception{
  when(service.listar()).thenReturn(List.of(new ProductoResponse(1L,"Teclado",new BigDecimal("100.00"))));
  mvc.perform(get("/api/productos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombre").value("Teclado"));
 }
 @Test void postInvalido() throws Exception{
  mvc.perform(post("/api/productos").contentType("application/json").content("{\"nombre\":\"\",\"precio\":0}"))
     .andExpect(status().isBadRequest());
 }
}
