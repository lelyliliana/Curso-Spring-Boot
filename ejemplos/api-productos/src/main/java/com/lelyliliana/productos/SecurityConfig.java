package com.lelyliliana.productos;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.databind.json.JsonMapper;
@Configuration
class SecurityConfig {
 @Bean PasswordEncoder encoder(){return PasswordEncoderFactories.createDelegatingPasswordEncoder();}
 @Bean UserDetailsService users(AppProperties p,PasswordEncoder encoder){return new InMemoryUserDetailsManager(
  User.withUsername("editor").password(encoder.encode(p.editorPassword())).authorities("PRODUCT_WRITE","OPS_READ").build(),
  User.withUsername("lector").password(encoder.encode(p.lectorPassword())).authorities("PRODUCT_READ").build());}
 @Bean SecurityFilterChain security(HttpSecurity http,JsonMapper mapper)throws Exception {
  return http.authorizeHttpRequests(a->a
   .requestMatchers("/api/csrf").permitAll()
   .requestMatchers("/actuator/health","/actuator/health/**").permitAll()
   .requestMatchers(HttpMethod.GET,"/api/productos/**","/api/categorias/**","/api/cotizaciones/**").permitAll()
   .requestMatchers("/actuator/**").hasAuthority("OPS_READ")
   .requestMatchers("/api/**").hasAuthority("PRODUCT_WRITE")
   .anyRequest().denyAll())
   .httpBasic(Customizer.withDefaults())
   .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->{
    res.setStatus(401);res.setHeader("WWW-Authenticate","Basic realm=\"curso\"");res.setContentType("application/problem+json");
    mapper.writeValue(res.getOutputStream(),ApiErrorHandler.problema(401,"UNAUTHORIZED","Autenticación requerida"));
   }).accessDeniedHandler((req,res,ex)->{res.setStatus(403);res.setContentType("application/problem+json");mapper.writeValue(res.getOutputStream(),ApiErrorHandler.problema(403,"FORBIDDEN","Acceso no permitido"));}))
   .build();
 }
}
