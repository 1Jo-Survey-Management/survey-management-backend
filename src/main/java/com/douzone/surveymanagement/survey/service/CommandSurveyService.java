package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.request.SurveyCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;

/**
 * 설문에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface CommandSurveyService {

    /**
     * 섦문에 대한 정보를 등록하는 메서드 입니다.
     *
     * @param surveyInfoCreateDto 설문에 대한 정보를 담은 Dto
     * @author : 강명관
     */
    void insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto);


    /**
     * 전체 설문을 등록하는 메서드 입니다.
     *
     * @param surveyCreateDto 설문에 대한 정보, 문항 정보, 선택지 정보를 담은 Dto
     * @author : 강명관
     */
    void insertSurvey(SurveyCreateDto surveyCreateDto);
}
