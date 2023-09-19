package com.douzone.surveymanagement.tag.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 태그 테이블에 대한 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class Tag {

    private final int tagNo;
    private final String tagName;
}
