package com.douzone.surveymanagement.user.filter;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomOAuth2Filter 입니다.
 * @author 김선규
 */
@Slf4j
public class CustomOAuth2Filter extends AbstractAuthenticationProcessingFilter {

    public CustomOAuth2Filter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    }

    @Override
    public CustomAuthentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationCheck != null && authenticationCheck.isAuthenticated()) {
            System.out.println("(OAuth2Filter)이미 인증된 Authentication OAuth2Filter");
            return (CustomAuthentication) authenticationCheck;
        }else{
            String accessToken = extractAccessTokenFromRequest(request);

            String naverCodeTokenCheck = request.getServletPath();

            System.out.println("OAuth2Filter accessToken : " + accessToken);
            System.out.println("OAuth2Filter naverPath : " + naverCodeTokenCheck);

            // 토큰도 없고 첫 회원가입도 아니면 접근 거부
            if (accessToken == null && !naverCodeTokenCheck.equals("/login/oauth2/code/naver")) {
                log.error("부적절한 접근!");
                return null;
            }

            CustomAuthenticationToken authRequest = new CustomAuthenticationToken(accessToken, null, naverCodeTokenCheck);

            CustomAuthentication authentication = (CustomAuthentication) getAuthenticationManager().authenticate(authRequest);

            if (authentication==null) {
                return null;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

//        String accessToken = (String) authResult.getCredentials();
//        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
//
//            if(authResult!=null && accessToken !=null){
//                String headerAccessToken = "Bearer " +  accessToken;
//                System.out.println("한번 보자1" + authResult.getDetails().toString());
//                System.out.println("어디쪽 꺼냐면 : " + authResult.getDetails().toString());
//                System.out.println("한번 보자2" + headerAccessToken);
//
//                response.setHeader("Access-Token", headerAccessToken);
//                chain.doFilter(request, response);
//
//        }

        chain.doFilter(request, response);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException{

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed");
    }

    private String extractAccessTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
