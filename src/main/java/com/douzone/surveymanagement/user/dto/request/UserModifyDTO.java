package com.douzone.surveymanagement.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 닉네임 수정 DTO 클래스입니다.
 *
 * @version 1.0
 */
@Schema(description = "유저 정보 수정을 위한 DTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModifyDTO {
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;
    @Schema(description = "사용자 닉네임", example = "nickname123")
    @NotBlank(message = "중복되는 닉네임입니다.")
    private String userNickname;
    @Schema(description = "사용자 생년월일", example = "1990-01-01")
    private Date userBirth;
    @Schema(description = "사용자 성별", example = "남성")
    private String userGender;
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String userEmail;

    @Schema(description = "사용자 프로필 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String userImage;
    @Schema(description = "계정 생성 시각", example = "2023-01-01T00:00:00.000+00:00")
    private Date createdAt;
}
