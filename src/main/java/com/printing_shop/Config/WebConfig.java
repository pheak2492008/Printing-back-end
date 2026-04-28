package com.printing_shop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the actual project root directory dynamically
        String projectRoot = System.getProperty("user.dir");
        String uploadDir = "file:" + projectRoot + "/uploads/";

        System.out.println("=== UPLOAD DIRECTORY CONFIGURED AS: " + uploadDir + " ===");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir)
                .setCachePeriod(3600 * 24)
                .resourceChain(true);

        // Fallback options
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/", "file:uploads/");
    }

    @Bean
    public OncePerRequestFilter corpFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {
                response.setHeader("Cross-Origin-Resource-Policy", "cross-origin");
                filterChain.doFilter(request, response);
            }
        };
    }
}