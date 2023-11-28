package com.douzone.surveymanagement.mysurvey.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그 정보 DTO 클래스입니다.
 *
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "설문 태그 정보 DTO")
public class MySurveyTagDTO {
    @Schema(description = "태그 번호", example = "1")
    private int tagNo;
    @Schema(description = "태그 이름들", example = "일상, 기타")
    private String tagNames;
}
