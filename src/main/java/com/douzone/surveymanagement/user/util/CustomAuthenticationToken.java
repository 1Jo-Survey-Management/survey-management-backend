package com.douzone.surveymanagement.user.util;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String customToken;

    public CustomAuthenticationToken(String customToken) {
        super(null, null);
        this.customToken = customToken;
    }

    public String getCustomToken() {
        return customToken;
    }
}



