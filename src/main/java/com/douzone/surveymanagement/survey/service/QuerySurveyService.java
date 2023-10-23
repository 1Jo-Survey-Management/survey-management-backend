package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;

import java.util.List;

/**
 *
 * mapper를 통해 가져온 데이터의 로직을 처리하기 위한 서비스 인터페이스 입니다.
 *
 * @author  : 엄아진
 * @since  : 1.0
 *
 *
 */

public interface QuerySurveyService {
    List<SurveyDetailInfoDto> readWeeklySurvey();

    List<SurveyDetailInfoDto> readRecentSurvey();

    List<SurveyDetailInfoDto> readClosingSurvey();
    List<SurveyDetailInfoDto> getSurveyAll(int page);

    List<SurveyDetailInfoDto> selectClosing(int page);
    List<SurveyDetailInfoDto> selectPost(int page);
}
