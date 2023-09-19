package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.mapper.SurveyMapper;
import com.douzone.surveymanagement.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyServiceImpl implements SurveyService {

    private final SurveyMapper surveyMapper;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto) {
        surveyMapper.insertSurveyInfo(surveyInfoCreateDto);
    }
}

