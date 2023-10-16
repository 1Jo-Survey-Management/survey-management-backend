package com.douzone.surveymanagement.surveyquestion.service;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;

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
     * @param surveyNo 설문 번호
     * @param surveyQuestionCreateDtoList 문항 리스트
     * @author : 강명관
     */
    void insertQuestionList(long surveyNo,
                            List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList);
}
