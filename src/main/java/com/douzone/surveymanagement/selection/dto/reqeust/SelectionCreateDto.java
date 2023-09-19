package com.douzone.surveymanagement.selection.dto.reqeust;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 선택지를 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
@Builder
public class SelectionCreateDto {

    @NotNull(message = "문항 번호는 null일 수 업습니다.")
    private long surveyQuestionNo;

    private long surveyQuestionMoveNo;

    @NotNull(message = "선택지 내용은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "선택지 내용은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String selectionValue;

    @NotNull(message = "이동 여부는 null일 수 없습니다.")
    private boolean isMovable;
}
