package com.douzone.surveymanagement.survey.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문조사 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class Survey {

    private final long surveyNo;
    private final long userNo;
    private final int surveyStatusNo;
    private final int openStatusNo;
    private final String surveyTitle;
    private final String surveyDescription;
    private final String surveyImage;
    private final LocalDateTime createdAt;
    private final LocalDateTime surveyPostAt;
    private final LocalDateTime surveyClosingAt;
    private final boolean isDeleted;

}
