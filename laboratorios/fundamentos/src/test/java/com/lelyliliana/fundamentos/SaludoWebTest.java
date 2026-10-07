package com.lelyliliana.fundamentos;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class SaludoWebTest {
 @Autowired MockMvc mvc;
 @Test void getContrato()throws Exception{mvc.perform(get("/api/saludos?nombre=Leli")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/json")).andExpect(jsonPath("$.mensaje").value("Hola, Leli"));}
 @Test void invalido400()throws Exception{mvc.perform(get("/api/saludos").param("nombre"," ")).andExpect(status().isBadRequest());}
 @Test void metodo405()throws Exception{mvc.perform(post("/api/saludos")).andExpect(status().isMethodNotAllowed());}
}
