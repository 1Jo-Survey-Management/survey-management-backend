package com.douzone.surveymanagement.common.security;

//import com.douzone.surveymanagement.user.util.CustmOAuth2UserService;
import com.douzone.surveymanagement.user.filter.CustomOAuth2Filter;
import com.douzone.surveymanagement.user.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//import com.douzone.surveymanagement.user.util.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)

public class SecurityConfig {

//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final CustmOAuth2UserService custmOAuth2UserService;
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

//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManagerBuilder auth) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http    .authorizeHttpRequests(authorize -> authorize
                      .antMatchers("/login/**").permitAll()
                        .anyRequest().authenticated());

//        auth    .authenticationProvider(customAuthenticationProvider());
//
        http        .addFilterBefore(customOAuth2Filter(),
                        UsernamePasswordAuthenticationFilter.class);


//        http    .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint().baseUri("/oauth2.0/authorize")
//                        .and()
//                        .redirectionEndpoint().baseUri("/login/oauth2/code/naver")
//                        .and()
//                               .userInfoEndpoint().userService(custmOAuth2UserService)
//                               .and()
//                                .successHandler(oAuth2AuthenticationSuccessHandler));
//                        .defaultSuccessUrl("http://localhost:3000/survey/main",true));
//
//        http    .logout(oauth2 -> oauth2
//                        .logoutSuccessUrl("http://localhost:3000")
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll());


        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    //Bean을 AUTHENTICATION_MANAGER에 매칭

    @Bean
    public CustomOAuth2Filter customOAuth2Filter() throws Exception {
        String redirectUrl = "http://localhost:3000";
        CustomOAuth2Filter filter = new CustomOAuth2Filter("/login/**",redirectUrl); // "/login/**"은 OAuth 2.0 토큰을 받을 엔드포인트 URL입니다.
        System.out.println("(SecurityConfig) filter : " + filter);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }



    /**
     * 프로토콜: http, 호스트: localhost, 포트: 3000번에 대해서
     * 모든 메서드(GET, POST, DELETE, PUT etc..)에 대해 CORS를 허용하기 위한 설정입니다.
     *
     * @return corsConfigurationSource
     * @author : 강명관
     */
//    configuration.setAllowedOrigins(List.of("http://localhost:3000","https://nid.naver.com"));
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("/**"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     *
     //     */
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.naverClientRegistration());
//    }
//
//    private ClientRegistration naverClientRegistration() {
//        String clientId = env.getProperty("spring.security.oauth2.client.registration.naver.client-id");
//        String clientSecret = env.getProperty("spring.security.oauth2.client.registration.naver.client-secret");
//        String redirectUri = env.getProperty("spring.security.oauth2.client.registration.naver.redirect-uri");
//        String scope = env.getProperty("spring.security.oauth2.client.registration.naver.scope");
//
//
//        return ClientRegistration.withRegistrationId("naver")
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUriTemplate(redirectUri) // redirectUri 필드 설정
//                .scope(scope)
//                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
//                .tokenUri("https://nid.naver.com/oauth2.0/token")
//                .userInfoUri("https://openapi.naver.com/v1/nid/me")
//                .userNameAttributeName("id")
//                .clientName("Naver")
//                .build();
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.httpBasic().disable();
//        http.csrf().disable(); // 외부 POST 요청을 받아야하니 csrf는 꺼준다.
//        http.cors(); // ⭐ CORS를 커스텀하려면 이렇게
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeHttpRequests()
//                .requestMatchers("/**").permitAll()
//                .anyRequest().authenticated();
//
//        return http.build();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/", "/login/**")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated()
//                )
//                .oauth2Login(withDefaults());
//    }

}
