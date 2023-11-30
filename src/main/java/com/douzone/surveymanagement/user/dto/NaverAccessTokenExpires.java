package com.douzone.surveymanagement.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 네이버 로그인 시 프로필 응답 dto 입니다
 *
 * @author 김선규
 */
@Getter
@Setter
public class NaverAccessTokenExpires {
    @JsonProperty("resultcode")
    private String resultcode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private result response;

    @Getter
    @Setter
    public static class result {
        private String token;
        private String expire_date;
        private String allowed_profile;

    }
}
