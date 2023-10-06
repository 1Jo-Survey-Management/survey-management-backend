package com.douzone.surveymanagement.user.filter;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomOAuth2Filter extends AbstractAuthenticationProcessingFilter {
    private final String loginRedirectUrl; // Access Token이 없을 때 리다이렉트할 URL

    public CustomOAuth2Filter(String defaultFilterProcessesUrl, String loginRedirectUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        this.loginRedirectUrl = loginRedirectUrl;
    }

    @Override
    public CustomAuthentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        System.out.println("헤더의 Authorization : " + request.getHeader("Authorization"));

        System.out.println("request 체크 : " + request.getServletPath());

        // OAuth 2.0 토큰을 추출하는 로직을 여기에 작성합니다.
        String accessToken = extractAccessTokenFromRequest(request);
        String userNoHeader = request.getHeader("userNo");
        Long userNo = null;
        String naverCodeTokenCheck = request.getServletPath();

        if (userNoHeader != null) {
            try {
                userNo = Long.parseLong(userNoHeader);
            } catch (NumberFormatException e) {
                e.getStackTrace();
            }
        }

        System.out.println("추출한 토큰 : " + accessToken);
        System.out.println("추출한 회원번호 : " + userNo);

        if (accessToken == null && !naverCodeTokenCheck.equals("/login/oauth2/code/naver") && !naverCodeTokenCheck.equals("/login/oauth2/code/naver/call")) {
            // Access Token이 없거나 네이버 CallBack이 아닌 경우 리다이렉트

            System.out.println("토큰 없을때 리다이렉트 경로 : " + loginRedirectUrl);
            response.sendRedirect(loginRedirectUrl);

            return null;
        } else if (naverCodeTokenCheck.equals("/login/oauth2/code/naver") || naverCodeTokenCheck.equals("/login/oauth2/code/naver/call")) {
            System.out.println("네이버 CallBack으로 통과");

            CustomAuthenticationToken authRequest = new CustomAuthenticationToken(null, null, naverCodeTokenCheck);

            CustomAuthentication authentication = (CustomAuthentication) getAuthenticationManager().authenticate(authRequest);

            System.out.println("authentication : " + authentication);

            return authentication;
        }

        // access token을 사용하여 AuthenticationToken 생성, 원래는 UsernamePasswordAuthenticationToken를 사용해야 하나, 이번
        // 로그인은 아이디, 비밀번호 로그인이 아니라 토큰을 이용한 인증이기 때문에 위 클래스를 커스텀하여 만듦
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(accessToken, null);
        CustomAuthenticationToken authRequest = new CustomAuthenticationToken(accessToken, userNo, null);

        System.out.println("authRequest : " + authRequest);

        // 추출한 토큰을 사용하여 사용자 인증을 수행
        // Default Authentication을 커스텀 한 것으로 사용
        CustomAuthentication authentication = (CustomAuthentication) getAuthenticationManager().authenticate(authRequest);

        if (authentication==null) {
            System.out.println("authentication null");
        }

        System.out.println("authentication : " + authentication);

        System.out.println("리턴 값 : " + authentication);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String accessToken = (String) authResult.getPrincipal();
        // 사용자 정보 추출 및 저장

        System.out.println("access Token 성공 : " + accessToken);
        System.out.println("회원 인증 : " + authResult.isAuthenticated());

        // 다음 필터로 이동
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException{

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed");
    }

    private String extractAccessTokenFromRequest(HttpServletRequest request) {
        //Authorization 헤더에서 Bearer Token을 추출
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분을 반환
        }
        return null;
    }
}
