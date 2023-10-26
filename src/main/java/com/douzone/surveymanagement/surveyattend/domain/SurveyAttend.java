package com.douzone.surveymanagement.surveyattend.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 참여 테이블에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class SurveyAttend {
    private final long surveyAttendNo;
    private final long userNo;
    private final long surveyNo;
    private final LocalDateTime surveyAttendCreatedAt;
}
