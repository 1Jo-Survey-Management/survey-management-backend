package com.douzone.surveymanagement.user.util;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String customToken;
    private final Long userNo;

    public CustomAuthenticationToken(String customToken, Long userNo) {
        super(null, null);
        this.customToken = customToken;
        this.userNo =userNo;
    }
}



