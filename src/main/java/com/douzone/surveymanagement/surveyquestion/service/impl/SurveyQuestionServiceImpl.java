package com.douzone.surveymanagement.surveyquestion.service.impl;

import com.douzone.surveymanagement.selection.service.SelectionService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.surveyquestion.mapper.SurveyQuestionMapper;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문 문항에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final SurveyQuestionMapper surveyQuestionMapper;

    private final SelectionService selectionService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurveyQuestion(SurveyQuestionCreateDto surveyQuestionCreateDto) {
//
//        surveyQuestionMapper.insertSurveyQuestion(surveyQuestionCreateDto);
//
//        surveyQuestionCreateDto.getSelectionCreateDtoList().forEach(
//            selectionService::insertSelection
//        );

    }
}
