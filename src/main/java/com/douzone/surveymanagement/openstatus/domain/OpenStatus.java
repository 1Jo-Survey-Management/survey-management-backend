package com.douzone.surveymanagement.openstatus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 설문 공개 상태 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class OpenStatus {
    private final int openStatusNo;
    private final String openStatusString;
}
