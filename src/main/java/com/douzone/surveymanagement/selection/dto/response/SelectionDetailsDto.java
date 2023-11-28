package com.douzone.surveymanagement.selection.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 선택지에 대한 모든 정보를 담은 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "설문 선택지에 대한 상세 정보를 담는 DTO")
public class SelectionDetailsDto {

    @Schema(description = "설문 문항 번호", example = "1")
    @JsonProperty("questionId")
    private long surveyQuestionNo;

    @Schema(description = "선택지 번호", example = "1")
    @JsonProperty("selectionId")
    private long selectionNo;
    @Schema(description = "설문 문항 이동 번호", example = "1")
    private long surveyQuestionMoveNo;
    @Schema(description = "선택지 값", example = "선택지 예시")
    private String selectionValue;
    @Schema(description = "선택지 이동 가능 여부", example = "true")
    private boolean isMovable;
    @Schema(description = "설문 종료 여부", example = "false")
    private boolean isEndOfSurvey;

}
