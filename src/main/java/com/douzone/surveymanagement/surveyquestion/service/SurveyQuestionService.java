package com.douzone.surveymanagement.surveyquestion.service;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;

/**
 * 설문 문항에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface SurveyQuestionService {

    /**
     * 설문 문항을 등록하는 메서드 입니다.
     *
     * @param surveyQuestionCreateDto 설문 문항에 대한 정보를 담음 Dto
     * @author : 강명관
     */
    void insertSurveyQuestion(SurveyQuestionCreateDto surveyQuestionCreateDto);

}
