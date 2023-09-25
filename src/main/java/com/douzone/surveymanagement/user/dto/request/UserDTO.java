package com.douzone.surveymanagement.user.dto.request;

import lombok.*;

import java.util.Date;

/**
 * 유저 정보 DTO 클래스입니다.
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDTO {
    private long userNo;
    private String userNickname;
    private Date userBirth;
    private String userGender;
    private String userEmail;
    private String userImage;
    private Date createdAt;
}
