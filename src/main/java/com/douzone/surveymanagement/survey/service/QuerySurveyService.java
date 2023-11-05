package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;

import java.util.List;

/**
 * 설문 조회에 대한 비즈니스 로직 메서드를 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface QuerySurveyService {

    /**
     * 설문에 대한 상세 정보(설문 정보, 설문의 문항들, 문항의 선택지들)를 조회하는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문 정보, 설문 문항, 설문 선택지를 담은 SurveyDetailsDto
     * @author : 강명관
     */
    SurveyDetailsDto findSurveyDetails(long surveyNo);

    /**
     * 해당 설문 번호에 대한 설문 대표 이미지가 저장된 Path를 가져오는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @return
     * @author : 강명관
     */
    String findSurveyImageBySurveyNo(long surveyNo);

    List<SurveyDetailInfoDto> readWeeklySurvey();

    List<SurveyDetailInfoDto> readRecentSurvey();

    List<SurveyDetailInfoDto> readClosingSurvey();

    List<SurveyDetailInfoDto> getSurveyAll(int page);

    List<SurveyDetailInfoDto> selectClosing(int page);

    List<SurveyDetailInfoDto> selectPost(int page);
}


