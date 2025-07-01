package com.barcodehub.eccomerce_spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.barcodehub.eccomerce_spring.dto.errors.ErrorDetailDTO;
import com.barcodehub.eccomerce_spring.dto.errors.ErrorResponseDTO;
import com.barcodehub.eccomerce_spring.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static com.barcodehub.eccomerce_spring.constants.Roles.ADMIN;
import static com.barcodehub.eccomerce_spring.constants.Roles.SOCIO;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(
                                        "/docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**",
                                        "/v3/api-docs",
                                        "/swagger-resources/**",
                                        "/webjars/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(ADMIN)

                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }


    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());

            ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                    "403",
                    "No tienes permisos para realizar esta acción",
                    null
            );

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    null,
                    List.of(errorDetail)
            );

            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        };
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                    "401",
                    "Autenticación requerida",
                    null
            );

            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    null,
                    List.of(errorDetail)
            );

            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        };
    }

}