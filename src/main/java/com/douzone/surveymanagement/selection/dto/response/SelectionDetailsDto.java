package com.douzone.surveymanagement.selection.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SelectionDetailsDto {

    @JsonProperty("questionId")
    private long surveyQuestionNo;

    @JsonProperty("selectionId")
    private long selectionNo;
    private long surveyQuestionMoveNo;
    private String selectionValue;
    private boolean isMovable;
    private boolean isEndOfSurvey;

}
