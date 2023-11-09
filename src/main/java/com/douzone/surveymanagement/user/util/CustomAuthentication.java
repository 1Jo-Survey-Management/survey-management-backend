//package com.douzone.surveymanagement.user.util;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * token을 추가한 CustomAuthentication 입니다
// * @author 김선규
// */
//public class CustomAuthentication implements Authentication {
//    private final CustomUserDetails userDetails;
//    private final String customToken;
//
//    public CustomAuthentication(CustomUserDetails userDetails, String customToken) {
//        this.userDetails = userDetails;
//        this.customToken = customToken;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userDetails.getAuthorities();
//    }
//
//    @Override
//    public Object getCredentials() {
//        return customToken;
//    }
//
//    @Override
//    public Object getDetails() {
//        return userDetails;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return userDetails;
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return true;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//    }
//
//    @Override
//    public String getName() {
//        return userDetails.getUserEmail();
//    }
//}
