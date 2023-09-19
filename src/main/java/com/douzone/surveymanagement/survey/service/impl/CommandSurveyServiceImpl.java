package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.selection.service.SelectionService;
import com.douzone.surveymanagement.survey.dto.request.SurveyCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.mapper.CommandSurveyMapper;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import com.douzone.surveymanagement.surveytag.service.SurveyTagService;
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
public class CommandSurveyServiceImpl implements CommandSurveyService {

    private final CommandSurveyMapper commandSurveyMapper;
    private final SurveyQuestionService surveyQuestionService;
    private final SelectionService selectionService;
    private final SurveyTagService surveyTagService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto) {
        commandSurveyMapper.insertSurveyInfo(surveyInfoCreateDto);
        surveyInfoCreateDto.getSurveyTagCreateDtoList().forEach(
            surveyTagService::insertSurveyTag
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurvey(SurveyCreateDto surveyCreateDto) {

        insertSurveyInfo(surveyCreateDto.getSurveyInfoCreateDto());

        surveyCreateDto.getSurveyQuestionCreateDtoList()
            .forEach(surveyQuestionService::insertSurveyQuestion);

        surveyCreateDto.getSelectionCreateDtoList()
            .forEach(selectionService::insertSelection);

    }
}

