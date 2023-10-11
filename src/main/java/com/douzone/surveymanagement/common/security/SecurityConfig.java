package com.douzone.surveymanagement.common.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and()
            .csrf()
            .disable()
            .cors().configurationSource(corsConfigurationSource());
        return http.build();
    }

    /**
     * 프로토콜: http, 호스트: localhost, 포트: 3000번에 대해서
     * 모든 메서드(GET, POST, DELETE, PUT etc..)에 대해 CORS를 허용하기 위한 설정입니다.
     *
     * @return corsConfigurationSource
     * @author : 강명관
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
