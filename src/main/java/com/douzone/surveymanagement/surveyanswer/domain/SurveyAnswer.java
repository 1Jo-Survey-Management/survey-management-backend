package com.douzone.surveymanagement.surveyanswer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 답변에 테이블에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyAnswer {
    private final long surveyAnswerNo;
    private final long surveyAttendNo;
    private final long surveyQuestionNo;
    private final String surveySubjectiveAnswer;
}
