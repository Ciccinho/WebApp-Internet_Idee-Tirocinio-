package it.internetidee.AbbreCatSin.config;

import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")                          //rotte dove consentire il CORS
            .allowedOrigins("http://localhost:4200")             //dominio a cui è permesso effettuare richieste
            .allowedMethods("GET", "POST", "PUT", "DELETE")      //metodi consentiti    
            .allowedHeaders("Authorization", "Content-type")     //headers consentiti   
            .allowCredentials(true)                        //consente di inviare le credenziali come cookie 
            .maxAge(3600);                                           //tempo di risposta cache 
    }

}
