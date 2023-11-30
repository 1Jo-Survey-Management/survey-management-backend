package com.douzone.surveymanagement.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 dto 입니다
 *
 * @author 김선규
 */
@Getter
@Setter
@Schema(description = "사용자 정보 DTO")
public class UserInfo {
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;
    @Schema(description = "사용자 닉네임", example = "nickname123")
    private String userNickname;
    @Schema(description = "사용자 성별", example = "남성")
    private String userGender;
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String userEmail;
    @Schema(description = "사용자 생년월일", example = "1990-01-01")
    private Date userBirth;
    @Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/image.jpg")
    private String userImage;
    @Schema(description = "계정 생성 일시", example = "2023-01-01T00:00:00.000+00:00")
    private Date createdAt;
    @Schema(description = "액세스 토큰", example = "access_token_example")
    private String accessToken;
    @Schema(description = "토큰 만료 시간", example = "3600")
    private String expiresIn;
    @Schema(description = "리프레시 토큰", example = "refresh_token_example")
    private String refreshToken;
    @Schema(description = "이전 액세스 토큰", example = "old_access_token_example")
    private String oldAccessToken;
}