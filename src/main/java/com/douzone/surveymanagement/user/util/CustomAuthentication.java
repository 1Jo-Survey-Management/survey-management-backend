package com.douzone.surveymanagement.user.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthentication implements Authentication {
    private final CustomUserDetails userDetails; // 사용자 정보를 저장하는 사용자 지정 클래스
    private final String customToken;

    public CustomAuthentication(CustomUserDetails userDetails, String customToken) {
        this.userDetails = userDetails;
        this.customToken = customToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return customToken;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails.getEmail();
    }

    @Override
    public boolean isAuthenticated() {
        return true; // 사용자 지정 로직에 따라 인증 여부를 설정
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // 사용자 지정 로직에 따라 인증 여부를 설정
    }

    @Override
    public String getName() {
        return userDetails.getEmail();
    }
}
