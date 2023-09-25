package com.douzone.surveymanagement.user.util;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final String accessToken;

    public CustomAuthenticationToken(String accessToken) {
        super(null);
        this.accessToken = accessToken;
        setAuthenticated(false); // 인증되지 않은 상태로 초기화
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}


