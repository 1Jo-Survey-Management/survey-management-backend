package com.douzone.surveymanagement.user.domain;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 유저 도메인 클래스 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class User {
    private final Long userNo;
    private final String userNickName;
    private Date userBirth;
    private String userGender;
    private String userEmail;
    private String userImage;
    private LocalDateTime createdAt;
}
