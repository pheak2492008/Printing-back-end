package com.printing_shop.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
                // 1. PUBLIC: Auth & Swagger
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // 2. PUBLIC: Static Files (Product Images)
                .requestMatchers("/uploads/**").permitAll()

                // 3. PUBLIC: Product Viewing
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/product-details/**").permitAll()

                // 4. PUBLIC: Ordering & Reviews
                .requestMatchers("/api/orders/calculate", "/api/orders/create").permitAll()
                .requestMatchers("/api/orders/history/**", "/api/orders/{id}").permitAll()
                .requestMatchers("/api/order-items/**").permitAll()
                .requestMatchers("/api/materials/**", "/api/v1/materials/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll() 
                .requestMatchers(HttpMethod.POST, "/api/v1/reviews/add").permitAll() 
                
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers("/api/v1/materials/**", "/api/materials/**").permitAll()
                
                // 4. ORDERS (Fixes 403 on /api/v1/orders/getall)
                // We permit all here so the dashboard can fetch the list. 
                .requestMatchers("/api/v1/orders/**").permitAll()
                .requestMatchers("/api/orders/**").permitAll()

                // 5. INVENTORY (Fixes 403 on /api/v1/inventory)
                .requestMatchers("/api/v1/inventory/**").permitAll()
                .requestMatchers("/api/inventory/**").permitAll()

                // 5. ADMIN ONLY: Management & Inventory
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/product-details/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/admin/**", "/api/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/inventory/**", "/api/v1/inventory/**").hasAuthority("ADMIN")
                .requestMatchers("/api/orders/getall").hasAuthority("ADMIN")

                // 6. CATCH-ALL
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", "http://localhost:5173", "http://localhost:5174",
                "https://your-frontend-link.onrender.com" // UPDATE THIS
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                .addSecuritySchemes("BearerAuth",
                    new SecurityScheme()
                        .name("BearerAuth")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }

    @Bean
    public CommandLineRunner printSwaggerLink() {
        return args -> {
            System.out.println("\n🚀 Printing Shop API Started Successfully!");
            System.out.println("👉 SWAGGER UI: http://localhost:8082/swagger-ui/index.html\n");
        };
    }
}