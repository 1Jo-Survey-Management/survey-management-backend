package com.douzone.surveymanagement.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 네이버 유저 dto
 *
 * @author 김선규
 */
@Getter
@Setter
public class NaverUserInfoResponse {
    @JsonProperty("resultcode")
    private String resultCode;

    private String message;

    private NaverUserInfo response;

    @Getter
    @Setter
    public static class NaverUserInfo {
        private String id;
        private String nickname;
        private String email;
        private String name;
        private String token;
        private String expire_date;
        private String allowed_profile;

    }
}
