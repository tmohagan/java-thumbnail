package com.tim_ohagan.thumbnail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/generate")  
                        .allowedOrigins(
                        "http://localhost:3000",
                        "https://www.tim-ohagan.com"
                )   
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*");
            }
        };
    }
}