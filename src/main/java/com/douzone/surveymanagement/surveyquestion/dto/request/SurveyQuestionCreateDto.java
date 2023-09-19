package com.douzone.surveymanagement.surveyquestion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
@Builder
public class SurveyQuestionCreateDto {

    private int surveyNo;
    private int questionTypeNo;
    private String surveyQuestionTitle;
    private String surveyQuestionDescription;
    boolean isRequired;
}
