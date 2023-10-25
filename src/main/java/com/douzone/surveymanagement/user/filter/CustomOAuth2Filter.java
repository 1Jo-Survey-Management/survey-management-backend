package com.douzone.surveymanagement.user.filter;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
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
public class CustomOAuth2Filter extends AbstractAuthenticationProcessingFilter {

    public CustomOAuth2Filter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    }

    @Override
    public CustomAuthentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        String accessToken = extractAccessTokenFromRequest(request);

        String naverCodeTokenCheck = request.getServletPath();

        if (accessToken == null && !naverCodeTokenCheck.equals("/login/oauth2/code/naver")) {
            return null;
        } else if (naverCodeTokenCheck.equals("/login/oauth2/code/naver") ) {

            CustomAuthenticationToken authRequest = new CustomAuthenticationToken(null, null, naverCodeTokenCheck);

            CustomAuthentication authentication = (CustomAuthentication) getAuthenticationManager().authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }

        CustomAuthenticationToken authRequest = new CustomAuthenticationToken(accessToken, null, null);

        CustomAuthentication authentication = (CustomAuthentication) getAuthenticationManager().authenticate(authRequest);

        if (authentication==null) {
            return null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

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
