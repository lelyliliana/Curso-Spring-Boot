package com.lelyliliana.productos;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import tools.jackson.databind.json.JsonMapper;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FlujoIntegrationTest {
 @Autowired MockMvc mvc;@Autowired JsonMapper mapper;@Autowired ProductoRepository repo;
 @Test void creaConsultaActualizaYElimina()throws Exception{

  var body=mvc.perform(post("/api/productos").contentType("application/json").content("{\"sku\":\"FLOW-01\",\"nombre\":\"Libro nuevo\",\"precio\":25.50,\"categoriaId\":1}")).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
  long id=mapper.readTree(body).get("id").asLong();assertTrue(repo.existsBySku("FLOW-01"));
  mvc.perform(get("/api/productos/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.precio").value(25.5));
  String update="{\"sku\":\"FLOW-01\",\"nombre\":\"Actualizado\",\"precio\":30,\"categoriaId\":2,\"version\":0}";
  mvc.perform(put("/api/productos/"+id).contentType("application/json").content(update)).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(1));
  mvc.perform(put("/api/productos/"+id).contentType("application/json").content(update)).andExpect(status().isConflict());
  mvc.perform(delete("/api/productos/"+id)).andExpect(status().isNoContent()).andExpect(content().string(""));
  mvc.perform(get("/api/productos/"+id)).andExpect(status().isNotFound());
 }
 @Test void categoriaAusenteNoCrea()throws Exception{long antes=repo.count();mvc.perform(post("/api/productos").contentType("application/json").content("{\"sku\":\"FAIL-01\",\"nombre\":\"Libro\",\"precio\":10,\"categoriaId\":99999}")).andExpect(status().isNotFound());assertEquals(antes,repo.count());}
 @Test void paginaLimites()throws Exception{mvc.perform(get("/api/productos?tamano=101")).andExpect(status().isBadRequest());mvc.perform(get("/api/productos?pagina=999")).andExpect(status().isOk()).andExpect(jsonPath("$.contenido").isEmpty());}
}
