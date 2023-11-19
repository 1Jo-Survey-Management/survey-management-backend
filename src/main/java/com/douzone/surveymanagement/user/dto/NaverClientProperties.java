package com.douzone.surveymanagement.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("spring.security.oauth2.client.registration.naver")
public class NaverClientProperties {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String authorizationGrantType;
    private String redirectUri;
}
