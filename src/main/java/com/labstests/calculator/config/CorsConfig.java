package com.labstests.calculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Указывает путь, на который применяется CORS
                .allowedOrigins("http://localhost:8080") // Разрешенные источники (origins)
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Разрешенные HTTP-методы
    }
}



