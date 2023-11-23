package com.douzone.surveymanagement.user.dto;

import lombok.*;
import java.util.Date;

/**
 * 유저 정보 dto 입니다
 * @author 김선규
 */
@Getter
@Setter
public class UserInfo {
    private long userNo;
    private String userNickname;
    private String userGender;
    private String userEmail;
    private Date userBirth;
    private String userImage;
    private Date createdAt;
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String oldAccessToken;
}