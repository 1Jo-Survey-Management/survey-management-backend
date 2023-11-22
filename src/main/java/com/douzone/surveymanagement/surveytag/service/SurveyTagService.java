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
     * @param surveyTagCreateDto 설문번호와 태그번호가 들어가 있는 Dto
     * @author : 강명관
     */
    void insertSurveyTag(SurveyTagCreateDto surveyTagCreateDto);

    /**
     * 설문 번호를 통해 해당 설문의 태그를 모두 삭제하는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    void deleteSurveyTags(long surveyNo);
}
