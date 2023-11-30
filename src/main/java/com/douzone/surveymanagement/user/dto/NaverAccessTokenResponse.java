package com.douzone.surveymanagement.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 네이버 토큰 dto
 *
 * @author 김선규
 */
@Getter
@Setter
public class NaverAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String codeUrl;
    private String scope;

    @JsonProperty("error_description")
    private String errorDescription;

    private String error;

}