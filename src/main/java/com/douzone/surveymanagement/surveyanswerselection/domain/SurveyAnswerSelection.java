package com.douzone.surveymanagement.surveyanswerselection.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 답변 선택지 테이블에 대한 도메인 클래스입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyAnswerSelection {
    private final long surveyAnswerSelectionNo;
    private final long surveyAnswerNo;
    private final long selectionNo;
}
