package com.douzone.surveymanagement.user.dto.request;

import lombok.*;

/**
 * 유저 닉네임 수정 DTO 클래스입니다.
 *
 * @version 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserModifyDTO {
    private long userNo;

    private String userNickName;
}
