package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuerySurveyService {
    List<SurveyDetailInfoDto> readWeeklySurvey();

    List<SurveyDetailInfoDto> readRecentSurvey();

    List<SurveyDetailInfoDto> readClosingSurvey();
    List<SurveyDetailInfoDto> getSurveyAll();

    List<SurveyDetailInfoDto> selectClosing();
    List<SurveyDetailInfoDto> selectPost();
}
