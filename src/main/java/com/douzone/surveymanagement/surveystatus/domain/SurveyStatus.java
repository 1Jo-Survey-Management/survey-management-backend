package com.douzone.surveymanagement.surveystatus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 상태 테이블에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyStatus {
    private final int surveyStatusNo;
    private final String surveyStatusName;
}
