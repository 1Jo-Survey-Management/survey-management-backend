package com.douzone.surveymanagement.surveyquestion.service.impl;

import com.douzone.surveymanagement.questiontype.enums.QuestionTypeEnum;
import com.douzone.surveymanagement.selection.service.SelectionService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.surveyquestion.mapper.SurveyQuestionMapper;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문 문항에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
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
    public void insertQuestionList(long surveyNo,
                                   List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList) {

        surveyQuestionCreateDtoList.forEach(surveyQuestionCreateDto -> {
                surveyQuestionCreateDto.setSurveyNo(surveyNo);
                surveyQuestionMapper.insertSurveyQuestion(surveyQuestionCreateDto);
            }
        );

        List<Long> questionNoList = surveyQuestionCreateDtoList.stream()
            .map(SurveyQuestionCreateDto::getSurveyQuestionNo)
            .collect(Collectors.toList());

        for (SurveyQuestionCreateDto questionDto : surveyQuestionCreateDtoList) {
            QuestionTypeEnum questionTypeEnum =
                QuestionTypeEnum.convertTo(questionDto.getQuestionTypeNo());

            selectionService.insertSelectionList(
                questionTypeEnum,
                questionNoList,
                questionDto.getSurveyQuestionNo(),
                questionDto.getSelectionCreateDtoList()
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAllQuestionAndSelection(long surveyNo) {
        selectionService.deleteAllSelection(surveyNo);
        surveyQuestionMapper.deleteAllSurveyQuestionsBySurveyNo(surveyNo);
    }

    @Override
    public void updateQuestion(long surveyNo,
                               List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList) {
        selectionService.deleteAllSelection(surveyNo);
        surveyQuestionMapper.deleteAllSurveyQuestionsBySurveyNo(surveyNo);
        insertQuestionList(surveyNo, surveyQuestionCreateDtoList);
    }
}
