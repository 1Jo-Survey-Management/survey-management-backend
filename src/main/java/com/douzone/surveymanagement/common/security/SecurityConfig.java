package com.douzone.surveymanagement.common.security;

import com.douzone.surveymanagement.user.filter.CustomOAuth2Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

/**
 * 시큐리티 필터 설정입니다
 *
 * @author 김선규
 */
@EnableWebSecurity(debug = false)
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomOAuth2Filter customOAuth2Filter(AuthenticationManager authenticationManager) {
        CustomOAuth2Filter filter = new CustomOAuth2Filter("/**");
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .rememberMe().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(authorize -> authorize
            .anyRequest().authenticated());
        http.addFilterBefore(customOAuth2Filter(authenticationManager()),
            UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 프로토콜: http, 호스트: localhost, 포트: 3000번에 대해서 모든 메서드(GET, POST, DELETE, PUT etc..)에 대해 CORS를
     * 허용하기 위한 설정입니다.
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
