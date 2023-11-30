package com.douzone.surveymanagement.selection.dto.reqeust;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 선택지를 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
@Schema(description = "선택지를 등록하기 위한 DTO")
public class SelectionCreateDto {

    @Schema(description = "설문 문항 이동 번호", example = "1")
    @JsonProperty("questionMoveId")
    private long surveyQuestionMoveNo;

    @Schema(description = "선택지 내용", example = "선택지 예시")
    @NotNull(message = "선택지 내용은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "선택지 내용은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String selectionValue;

    @Schema(description = "이동 가능 여부", example = "true")
    @JsonProperty("isMoveable")
    @NotNull(message = "이동 여부는 null일 수 없습니다.")
    private boolean isMovable;

    @Schema(description = "설문 종료 여부", example = "false")
    @NotNull(message = "설문 종료 여부는 null일 수 없습니다.")
    private boolean isEndOfSurvey;

    public void setSurveyQuestionMoveNo(long surveyQuestionMoveNo) {
        this.surveyQuestionMoveNo = surveyQuestionMoveNo;
    }
}
