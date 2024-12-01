package com.project.books.management.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("http://localhost:4200") // Allow Angular app origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow HTTP methods
                .allowedHeaders("*")
                .allowCredentials(true); // Allow cookies if needed
    }
}
