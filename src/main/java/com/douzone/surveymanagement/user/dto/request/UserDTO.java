package com.douzone.surveymanagement.user.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long userNo;
    private String userNickname;
    private Date userBirth;
    private String userGender;
    private String userEmail;
    private byte[] userImage;
    private Date createdAt;
}
