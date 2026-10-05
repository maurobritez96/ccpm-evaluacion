package com.ccpm.evaluacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilitar CSRF (necesario para APIs REST sin estado)
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar política de sesión sin estado (Stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Definir permisos de las rutas
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (ej. login, registro, documentación)
                        .requestMatchers("/**").permitAll()
                        // Cualquier otra petición requerirá autenticación
                        .anyRequest().authenticated()
                )
                .build();
    }
}