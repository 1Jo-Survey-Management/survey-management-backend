package com.douzone.surveymanagement.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "설문 태그 정보 DTO")
public class TagDto {

    @Schema(description = "태그 번호", example = "1")
    private int tagNo;
    @Schema(description = "태그 이름", example = "일상")
    private String tagName;

}
