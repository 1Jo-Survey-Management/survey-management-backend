package com.douzone.surveymanagement.user.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 유저 닉네임 수정 DTO 클래스입니다.
 *
 * @version 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModifyDTO {
    private long userNo;
    @NotBlank(message = "중복되는 닉네임입니다.")
    private String userNickname;
    private Date userBirth;
    private String userGender;
    private String userEmail;
    private String userImage;
    private Date createdAt;
}
