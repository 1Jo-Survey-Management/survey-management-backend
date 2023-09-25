package com.douzone.surveymanagement.user.filter;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomOAuth2Filter extends AbstractAuthenticationProcessingFilter {

    private final String defaultFilterProcessesUrl;
    private final String loginRedirectUrl; // Access Token이 없을 때 리다이렉트할 URL


    public CustomOAuth2Filter(String defaultFilterProcessesUrl, String loginRedirectUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        this.defaultFilterProcessesUrl = defaultFilterProcessesUrl;
        this.loginRedirectUrl = loginRedirectUrl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // OAuth 2.0 토큰을 추출하는 로직을 여기에 작성합니다.
        String accessToken = extractAccessTokenFromRequest(request);

        System.out.println(request.getHeader("Authorization"));

        System.out.println("추출한 토큰 : " + accessToken);

        if (accessToken == null) {
            // Access Token이 없을 경우 리다이렉트\

//            response.sendRedirect(loginRedirectUrl);
            System.out.println("토큰 없을때 리다이렉트 경로 : " + loginRedirectUrl);
            return null;
        }

        // access token을 사용하여 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(accessToken, null);

        System.out.println("sdfsdf");

        // 추출한 토큰을 사용하여 사용자 인증을 수행합니다.
            Authentication authentication = getAuthenticationManager().authenticate(authRequest);

        if (authentication==null) {
            System.out.println("authentication null");
        }

        System.out.println("authentication : " + authentication);

//        successfulAuthentication(request,response,null,authentication);

        System.out.println("리턴 값 : " + authentication);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 인증이 성공한 경우 실행되는 부분입니다.
        // 여기에서 추가 작업을 수행할 수 있습니다.

        // 예시: 사용자 정보를 추출하고 세션에 저장
        String accessToken = (String) authResult.getPrincipal();
        // 사용자 정보 추출 및 저장

        System.out.println("access Token 성공 : " + accessToken);

        // 다음 필터로 이동합니다.
//        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 인증이 실패한 경우 실행되는 부분입니다.
        // 여기에서 실패 응답을 생성하거나 추가 작업을 수행할 수 있습니다.

        // 예시: 실패 응답 생성
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed");
    }

    private String extractAccessTokenFromRequest(HttpServletRequest request) {
        // 실제로 OAuth 2.0 토큰을 추출하는 로직을 작성해야 합니다.
        // 이 예제에서는 Authorization 헤더에서 Bearer Token을 추출하는 것으로 가정합니다.
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분을 반환
        }
        return null;
    }
}
