package com.printing_shop.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

            	    // 🔓 1. AUTH & SWAGGER (Always Public)
            	    .requestMatchers("/api/auth/**").permitAll()
            	    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

            	    // 🔓 2. THE ORDERING FLOW (Make this Public!)
            	    // This allows Guests to Calculate and Create Orders without logging in.
            	    .requestMatchers("/api/orders/calculate").permitAll()
            	    .requestMatchers("/api/orders/create").permitAll()
            	    .requestMatchers("/api/orders/history/**").permitAll() // Tracking by phone
            	    .requestMatchers("/api/orders/{id}").permitAll()      // Viewing receipt
            	    .requestMatchers("/uploads/**").permitAll()           // Viewing design files

            	    // 🔓 3. PRODUCT & MATERIAL INFO (Public)
            	    .requestMatchers("/api/materials/**", "/api/v1/materials/**").permitAll()
            	    .requestMatchers("/api/product-details/**").permitAll()
            	    .requestMatchers("/api/inventory/**").permitAll()

            	    // 🔐 4. ADMIN ONLY (Requires Login + ADMIN Role)
            	    // Secure the management parts so only you/staff can change things.
            	    .requestMatchers("/api/products/**").hasAuthority("ADMIN")
            	    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
            	    .requestMatchers("/api/orders/getall").hasAuthority("ADMIN") // Staff see all orders

            	    // 🔐 5. DEFAULT
            	    .anyRequest().authenticated()
            	)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ CORS CONFIG
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "http://localhost:5173",
            "http://localhost:8081"
        ));

        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin"
        ));

        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // ✅ Swagger JWT config
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

    // ✅ Print Swagger URL on start
    @Bean
    public CommandLineRunner printSwaggerLink() {
        return args -> {
            System.out.println("\n🚀 Application Started Successfully!");
            System.out.println("👉 SWAGGER UI: http://localhost:8081/swagger-ui/index.html\n");
        };
    }
}