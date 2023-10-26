package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.common.utils.FileUploadUtil;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import com.douzone.surveymanagement.survey.mapper.CommandSurveyMapper;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import com.douzone.surveymanagement.surveytag.service.SurveyTagService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private static final int NEXT_DEFAULT_INDEX = 1;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public long insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto, MultipartFile surveyImage) {

        String saveFilePath = FileUploadUtil.uploadFile(surveyImage);
        surveyInfoCreateDto.setSurveyImagePath(saveFilePath);

        commandSurveyMapper.insertSurveyInfo(surveyInfoCreateDto);

        long surveyNo = surveyInfoCreateDto.getSurveyNo();

        surveyInfoCreateDto.getSurveyTags().forEach((tagNo) ->
            surveyTagService.insertSurveyTag(
                new SurveyTagCreateDto(surveyNo, tagNo + NEXT_DEFAULT_INDEX)
            )
        );

        return surveyNo;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSurvey(SurveyInfoCreateDto surveyInfoCreateDto,
                             List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
                             MultipartFile surveyImage) {

        long surveyNo = insertSurveyInfo(surveyInfoCreateDto, surveyImage);

        surveyQuestionService.insertQuestionList(surveyNo, surveyQuestionCreateDtoList);
    }

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
    public void updateSurveyInfo(SurveyInfoUpdateDto surveyInfoUpdateDto,
                                 MultipartFile surveyImage) {

        if (Objects.nonNull(surveyImage)) {
            String fileUploadPath = FileUploadUtil.uploadFile(surveyImage);
            surveyInfoUpdateDto.setSurveyImage(fileUploadPath);
        }

        commandSurveyMapper.updateSurvey(surveyInfoUpdateDto);
    }

    @Transactional
    @Override
    public void updateSurvey(SurveyInfoUpdateDto surveyInfoUpdateDto,
                             MultipartFile surveyImage,
                             List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList) {

        updateSurveyInfo(surveyInfoUpdateDto, surveyImage);
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
}

