package com.printing_shop.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. Resource Handler for Uploads
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps http://localhost:8081/uploads/image.jpg to the physical folder
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/resources/static/uploads/")
                .setCachePeriod(0); // Useful during development to see changes immediately
    }

    // 2. Corrected CORS Mapping
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Changed from /api/** to /** to cover all bases
                .allowedOrigins(
                    "http://localhost:5173", 
                    "http://127.0.0.1:5173", 
                    "http://localhost:3000" // Common fallback for React
                ) 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*") // Allows all headers (Content-Type, Authorization, etc.)
                .exposedHeaders("Authorization") // Important if you use JWT later
                .allowCredentials(true)
                .maxAge(3600); // Cache the "Preflight" request for 1 hour
    }
}