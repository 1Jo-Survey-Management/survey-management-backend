package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import com.douzone.surveymanagement.survey.mapper.CommandSurveyMapper;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import com.douzone.surveymanagement.surveystatus.enums.SurveyStatusEnum;
import com.douzone.surveymanagement.surveytag.service.SurveyTagService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CommandSurveyServiceImpl implements CommandSurveyService {

    private final CommandSurveyMapper commandSurveyMapper;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyTagService surveyTagService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public long insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto) {

        checkSurveyPostAndInjectPostAt(surveyInfoCreateDto);
        commandSurveyMapper.insertSurveyInfo(surveyInfoCreateDto);

        long surveyNo = surveyInfoCreateDto.getSurveyNo();

        surveyTagService.insertAllSurveyTag(surveyNo, surveyInfoCreateDto.getSurveyTags());

        return surveyNo;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurvey(SurveyInfoCreateDto surveyInfoCreateDto,
                             List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList) {

        long surveyNo = insertSurveyInfo(surveyInfoCreateDto);

        surveyQuestionService.insertQuestionList(surveyNo, surveyQuestionCreateDtoList);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateSurveyStatusToDeadline() {
        commandSurveyMapper.updateSurveyStatusToDeadline();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateSurveyInfo(SurveyInfoUpdateDto surveyInfoUpdateDto) {
        commandSurveyMapper.updateSurvey(surveyInfoUpdateDto);

        surveyTagService.updateSurveyTags(
            surveyInfoUpdateDto.getSurveyNo(),
            surveyInfoUpdateDto.getSurveyTags()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateSurvey(SurveyInfoUpdateDto surveyInfoUpdateDto,
                             List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList) {

        updateSurveyInfo(surveyInfoUpdateDto);
        surveyQuestionService.updateQuestion(
            surveyInfoUpdateDto.getSurveyNo(),
            surveyQuestionCreateDtoList
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean updateSurveyStatusToPostInProgress(long surveyNo) {
        return commandSurveyMapper.updateSurveyStatusToPostFromInProgress(surveyNo) != 0;
    }

    /**
     * 설문등록시 설문의 상태(작성, 진행, 마감) 상태를 체크하고 진행상태일 경우 설문의 등록일을 현재 시간으로 넣어주는 메서드 입니다.
     *
     * @param surveyInfoCreateDto 설문을 등록하기 위한 dto
     * @author : 강명관
     */
    private static void checkSurveyPostAndInjectPostAt(SurveyInfoCreateDto surveyInfoCreateDto) {
        if (surveyInfoCreateDto.getSurveyStatusNo() ==
            SurveyStatusEnum.PROGRESS.getSurveyStatusNo()) {
            surveyInfoCreateDto.setSurveyPostAt(LocalDateTime.now());
        }
    }

}

