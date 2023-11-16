package com.douzone.surveymanagement.common.security;

import com.douzone.surveymanagement.user.dto.NaverClientProperties;
import com.douzone.surveymanagement.user.filter.CustomOAuth2Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * 시큐리티 필터 설정입니다
 * @author 김선규
 */
@EnableWebSecurity(debug = false)
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final NaverClientProperties naverClientProperties;


//    @Value("${spring.security.oauth2.client.registration.naver.client_id}")
//    private String naverClientId;
//
//    @Value("${spring.security.oauth2.client.registration.naver.client_secret}")
//    private String naverClientSecret;

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
    public String StringTest() {
        String clientId = naverClientProperties.getClientId();

        return clientId;
    }
    @Bean
    public InMemoryClientRegistrationRepository clientRegistrationRepository(String client_id) {

        ClientRegistration naverRegistration = ClientRegistration
                .withRegistrationId("naver")
                .clientId(client_id)
                .clientSecret("au4WnhNLFn")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .scope("openid", "profile", "email")
                .clientName("Naver")
                .build();

        return new InMemoryClientRegistrationRepository(naverRegistration);
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

        http    .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated());
		http	.oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository(StringTest()));

        http.   addFilterBefore(customOAuth2Filter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);


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
