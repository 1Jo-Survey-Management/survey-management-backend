package com.douzone.surveymanagement.surveytag.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 태그 테이블에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyTag {

    private final int surveyTagNo;
    private final long surveyNo;
    private final int tagNo;
}
