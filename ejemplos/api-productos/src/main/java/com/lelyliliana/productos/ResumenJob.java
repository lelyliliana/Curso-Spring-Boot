package com.lelyliliana.productos;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
@Component
@ConditionalOnProperty(name="app.tareas-habilitadas",havingValue="true")
class ResumenJob {
 private static final Logger log=LoggerFactory.getLogger(ResumenJob.class);
 private final ProductoRepository repo;
 ResumenJob(ProductoRepository repo){this.repo=repo;}
 @Scheduled(fixedDelayString="${app.resumen-delay:60000}") void ejecutar(){log.info("Resumen periódico: productos={}",repo.count());}
}
