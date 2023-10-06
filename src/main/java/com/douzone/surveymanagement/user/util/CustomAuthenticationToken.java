package com.douzone.surveymanagement.user.util;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String callBackUri;
    private final String customToken;
    private final Long userNo;

    public CustomAuthenticationToken(String customToken, Long userNo, String callBackUri) {
        super(null, null);
        this.callBackUri = Optional.ofNullable(callBackUri).orElse("");
        this.customToken = Optional.ofNullable(customToken).orElse("");
        this.userNo = Optional.ofNullable(userNo).orElse(0L);
    }
}



