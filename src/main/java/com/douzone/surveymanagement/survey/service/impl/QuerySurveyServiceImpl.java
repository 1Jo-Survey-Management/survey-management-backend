package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuerySurveyServiceImpl implements QuerySurveyService {
    private QuerySurveyMapper querySurveyMapper;

    public QuerySurveyServiceImpl(QuerySurveyMapper querySurveyMapper) {
        this.querySurveyMapper = querySurveyMapper;
    }

    @Override
    public List<SurveyDetailInfoDto> readWeeklySurvey() {
        return querySurveyMapper.weeklySurvey();
    }

    @Override
    public List<SurveyDetailInfoDto> readRecentSurvey() {

        return querySurveyMapper.recentSurvey();
    }

    @Override
    public List<SurveyDetailInfoDto> readClosingSurvey() {
        List<SurveyDetailInfoDto> surveyList = querySurveyMapper.closingSurvey();// 1.2~ 10 pk

        return surveyList;
    }

    @Override
    public List<SurveyDetailInfoDto> getSurveyAll() {
        return querySurveyMapper.readAllSurvey();
    }

    @Override
    public List<SurveyDetailInfoDto> selectClosing() {
        return querySurveyMapper.selectClosingSurvey();
    }

    @Override
    public List<SurveyDetailInfoDto> selectPost() {
        return querySurveyMapper.selectPostSurvey();
    }
}
