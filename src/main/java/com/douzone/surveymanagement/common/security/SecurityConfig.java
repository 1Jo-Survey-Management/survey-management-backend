package com.douzone.surveymanagement.common.security;

import com.douzone.surveymanagement.user.filter.CustomOAuth2Filter;
import com.douzone.surveymanagement.user.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
 * @author 김선규
 */
@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {
private final AuthenticationConfiguration authenticationConfiguration;
private final CustomAuthenticationProvider customAuthenticationProvider;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, CustomAuthenticationProvider customAuthenticationProvider){
        this.authenticationConfiguration = authenticationConfiguration;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager () throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
            .authorizeHttpRequests().anyRequest().permitAll().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http    .authorizeHttpRequests(authorize -> authorize
//                      .anyRequest().authenticated());
        http        .addFilterBefore(customOAuth2Filter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    /**
     * OAuth 로그인에 대한 필터입니다.
     * @param authenticationManager
     * @return filter
     * @author 김선규
     */
    @Bean
    public CustomOAuth2Filter customOAuth2Filter(AuthenticationManager authenticationManager) {
        CustomOAuth2Filter filter = new CustomOAuth2Filter("/**");
        filter.setAuthenticationManager(authenticationManager);
        return filter;
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
