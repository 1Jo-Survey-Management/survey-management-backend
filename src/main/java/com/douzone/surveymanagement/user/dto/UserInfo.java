package com.douzone.surveymanagement.user.dto;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
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

}