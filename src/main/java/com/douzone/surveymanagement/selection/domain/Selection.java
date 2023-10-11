package com.douzone.surveymanagement.selection.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 선택지 도메인 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class Selection {
    private final long selectionNo;
    private final long surveyQuestionNo;
    private final long surveyQuestionMoveNo;
    private final String selectionValue;
    private final boolean isMovable;
    private final boolean isEndOfSurvey;
}
