package com.douzone.surveymanagement.surveyquestion.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 문항에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyQuestion {
    private final long surveyQuestionNo;
    private final long surveyNo;
    private final int questionTypeNo;
    private final String surveyQuestionTitle;
    private final String surveyQuestionDescription;
    private final boolean isRequired;


}
