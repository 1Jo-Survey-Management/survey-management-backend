package com.douzone.surveymanagement.surveytag.service;

import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;

/**
 * 설문 태그에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface SurveyTagService {

    /**
     * 설문에 대한 태그를 저장하는 메서드 입니다.
     *
     * @param surveyTagCreateDto 설문번호, 설문 태그 번호를 갖고있는 Dto
     * @author : 강명관
     */
    void insertSurveyTag(SurveyTagCreateDto surveyTagCreateDto);
}
