package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 설문 조회에 대한 비즈니스 로직을 정의하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class QuerySurveyServiceImpl implements QuerySurveyService {

    private final QuerySurveyMapper querySurveyMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public SurveyDetailsDto findSurveyDetails(long surveyNo) {
        return querySurveyMapper.selectSurveyDetailsBySurveyNo(surveyNo);
    }

    @Override
    public String findSurveyImageBySurveyNo(long surveyNo) {
        return querySurveyMapper.selectSurveyImageBySurveyNo(surveyNo);
    }
}

