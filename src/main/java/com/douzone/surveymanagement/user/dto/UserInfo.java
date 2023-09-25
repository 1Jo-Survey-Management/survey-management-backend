package com.douzone.surveymanagement.user.dto;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfo {
//    private long user_no;
//    private String user_nickname;
//    private String user_gender;
    private String userEmail;
//    private Date user_birth;
//    private String user_image;
//    private Date created_at;
    private String accessToken;

}