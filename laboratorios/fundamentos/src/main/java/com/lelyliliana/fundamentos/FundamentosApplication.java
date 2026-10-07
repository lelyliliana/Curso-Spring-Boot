package com.lelyliliana.fundamentos;
import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;import java.time.Clock;
@SpringBootApplication
@EnableConfigurationProperties(SaludoProperties.class)
public class FundamentosApplication {
 public static void main(String[] args){SpringApplication.run(FundamentosApplication.class,args);}
 @Bean Clock clock(){return Clock.systemUTC();}
}
