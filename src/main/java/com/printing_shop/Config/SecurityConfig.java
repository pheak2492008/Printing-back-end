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
                .requestMatchers("/api/v1/auth/**", "/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                
                // Orders
                .requestMatchers("/api/v1/orders/getall", "/api/v1/orders/create", "/api/v1/orders/calculate").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/orders/*/status").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/*").permitAll()

                // Inventory & Materials (Path mapping fix)
                .requestMatchers("/api/materials/**", "/api/v1/materials/**").permitAll()
                .requestMatchers("/api/v1/inventory/**").permitAll() 
                
                // Products & Product Details (Unlock POST for Admin)
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/products/create").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/product-details/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/product-details/**").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/reviews/add").permitAll()

                .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
                "https://printing-shop-frontend.onrender.com"
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
            .components(new Components().addSecuritySchemes("BearerAuth",
                new SecurityScheme().name("BearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public CommandLineRunner printSwaggerLink() {
        return args -> {
            System.out.println("\n🚀 Printing Shop API Started Successfully!");
            System.out.println("👉 API BASE: http://localhost:8081/api/v1");
            System.out.println("👉 SWAGGER UI: http://localhost:8081/swagger-ui/index.html\n");
        };
    }
}