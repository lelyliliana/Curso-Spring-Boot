package com.lelyliliana.productos;
import java.io.IOException;import java.util.UUID;
import jakarta.servlet.*;import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.*;
@Component
@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
class RequestIdFilter extends OncePerRequestFilter {
 private static final Logger log=LoggerFactory.getLogger(RequestIdFilter.class);
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String supplied=req.getHeader("X-Request-Id");
  String id=supplied!=null && supplied.matches("[A-Za-z0-9-]{1,64}")?supplied:UUID.randomUUID().toString();
  res.setHeader("X-Request-Id",id);MDC.put("requestId",id);
  try{chain.doFilter(req,res);}finally{log.info("HTTP method={} status={}",req.getMethod(),res.getStatus());MDC.remove("requestId");}
 }
}
