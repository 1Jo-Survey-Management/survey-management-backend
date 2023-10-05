package com.douzone.surveymanagement.surveytag.dto.request;

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

    private long surveyNo;

    private int tagNo;
}
