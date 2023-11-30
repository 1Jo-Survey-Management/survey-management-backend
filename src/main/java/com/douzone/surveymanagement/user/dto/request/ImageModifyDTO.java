package com.douzone.surveymanagement.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이미지 정보 수정 DTO 클래스입니다.
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "사용자 이미지 정보 수정을 위한 DTO")
public class ImageModifyDTO {
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;
    @Schema(description = "사용자 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String userImage;
}
