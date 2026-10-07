package com.lelyliliana.productos;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.jupiter.api.Assertions.*;
import tools.jackson.databind.json.JsonMapper;
@SpringBootTest(properties={"app.editor-password=prueba-editor","app.lector-password=prueba-lector"})
@AutoConfigureMockMvc
@Transactional
class FlujoIntegrationTest {
 @Autowired MockMvc mvc;@Autowired JsonMapper mapper;@Autowired ProductoRepository repo;
 @Test void creaConsultaActualizaYElimina()throws Exception{
  var auth=user("editor").authorities(()->"PRODUCT_WRITE");
  var body=mvc.perform(post("/api/productos").with(auth).with(csrf()).contentType("application/json").content("{\"sku\":\"FLOW-01\",\"nombre\":\"Libro nuevo\",\"precio\":25.50,\"categoriaId\":1}")).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
  long id=mapper.readTree(body).get("id").asLong();assertTrue(repo.existsBySku("FLOW-01"));
  mvc.perform(get("/api/productos/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.precio").value(25.5));
  String update="{\"sku\":\"FLOW-01\",\"nombre\":\"Actualizado\",\"precio\":30,\"categoriaId\":2,\"version\":0}";
  mvc.perform(put("/api/productos/"+id).with(auth).with(csrf()).contentType("application/json").content(update)).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(1));
  mvc.perform(put("/api/productos/"+id).with(auth).with(csrf()).contentType("application/json").content(update)).andExpect(status().isConflict());
  mvc.perform(delete("/api/productos/"+id).with(auth).with(csrf())).andExpect(status().isNoContent()).andExpect(content().string(""));
  mvc.perform(get("/api/productos/"+id)).andExpect(status().isNotFound());
 }
 @Test void categoriaAusenteNoCrea()throws Exception{long antes=repo.count();mvc.perform(post("/api/productos").with(user("editor").authorities(()->"PRODUCT_WRITE")).with(csrf()).contentType("application/json").content("{\"sku\":\"FAIL-01\",\"nombre\":\"Libro\",\"precio\":10,\"categoriaId\":99999}")).andExpect(status().isNotFound());assertEquals(antes,repo.count());}
 @Test void saludPublicaYMetricasProtegidas()throws Exception{mvc.perform(get("/actuator/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP")).andExpect(jsonPath("$.components").doesNotExist());mvc.perform(get("/actuator/metrics")).andExpect(status().isUnauthorized());mvc.perform(get("/actuator/metrics").with(user("ops").authorities(()->"OPS_READ"))).andExpect(status().isOk());}
 @Test void paginaLimites()throws Exception{mvc.perform(get("/api/productos?tamano=101")).andExpect(status().isBadRequest());mvc.perform(get("/api/productos?pagina=999")).andExpect(status().isOk()).andExpect(jsonPath("$.contenido").isEmpty());}
 @Test void requestIdAcotado()throws Exception{mvc.perform(get("/api/productos").header("X-Request-Id","prueba-1")).andExpect(header().string("X-Request-Id","prueba-1"));mvc.perform(get("/api/productos").header("X-Request-Id","x".repeat(100))).andExpect(header().string("X-Request-Id",org.hamcrest.Matchers.matchesPattern("[0-9a-f-]{36}")));}
}
