package com.douzone.surveymanagement.selection.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
@Builder
public class SelectionCreateDto {
    private long surveyQuestionNo;
    private long surveyQuestionMoveNo;
    private String selectionValue;
    private boolean isMovable;
}
