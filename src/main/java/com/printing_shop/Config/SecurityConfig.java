package com.printing_shop.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Added for Method specific rules
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Added
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
@EnableMethodSecurity // 🔥 Enables @PreAuthorize("hasAuthority('ADMIN')") in Controllers
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

                // 🔓 PUBLIC: Auth & Swagger
                .requestMatchers("/api/auth/**", "/api/v1/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // 🔓 PUBLIC: Viewing Products & Details (GET only)
                .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product-details/**").permitAll()
                
                // 🔐 ADMIN ONLY: Creating, Updating, Deleting
                .requestMatchers(HttpMethod.POST, "/api/products/**", "/api/v1/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/v1/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/product-details/**").hasAuthority("ADMIN")
                
                // 🔐 OTHER ADMIN PATHS
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/orders/getall").hasAuthority("ADMIN")

                // 🔓 PUBLIC: Orders & Static Files
                .requestMatchers("/api/orders/**", "/uploads/**").permitAll()
                .requestMatchers("/api/materials/**", "/api/inventory/**").permitAll()

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

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5178", // ✅ Added your Frontend port
                "http://localhost:8081"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization"));
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
            System.out.println("\n🚀 Printing Shop API Started!");
            System.out.println("👉 Swagger: http://localhost:8081/swagger-ui/index.html\n");
        };
    }
}