package com.isac.agendadortarefas.infrastructure.configs;


import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* Cors é uma configuração de segurança implementada pelos navegadores
* É um aditivo de segurança no backend para permitir apenas que uma origem reconhecida
* faça requisições à nossa aplicação.
* */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer configCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**") //libera para todos os endpoints
                        .allowedOrigins("http://localhost:4200") //origem que pode acessar a aplicação
                        .allowedMethods("GET", "POST", "PUT","PATCH", "DELETE") //métodos  http que são permitidos
                        .allowedHeaders("*")
                        .allowCredentials(true) //permite cookes e headers
                        .maxAge(360);

            }
        };
    }
}
