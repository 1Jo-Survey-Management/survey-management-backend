package com.douzone.surveymanagement.surveytag.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 설문 태그를 작성하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
public class SurveyTagCreateDto {

//    @NotNull(message = "설문 번호는 null일 수 없습니다.")
//    private long surveyNo;

    @NotNull(message = "태그 번호는 null일 수 없습니다.")
    private int tagNo;
}
