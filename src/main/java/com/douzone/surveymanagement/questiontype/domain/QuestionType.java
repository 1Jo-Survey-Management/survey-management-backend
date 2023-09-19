package com.douzone.surveymanagement.questiontype.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 문항 타입 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class QuestionType {

    private final int questionTypeNo;
    private final String questionTypeName;
}
