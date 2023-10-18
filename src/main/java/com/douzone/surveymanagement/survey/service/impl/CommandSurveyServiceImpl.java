package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.common.exception.NotAcceptableFileException;
import com.douzone.surveymanagement.common.utils.FileUploadUtil;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.mapper.CommandSurveyMapper;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.surveyquestion.service.SurveyQuestionService;
import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import com.douzone.surveymanagement.surveytag.service.SurveyTagService;
import java.io.IOException;
import java.util.List;
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
@Transactional(readOnly = true)
public class CommandSurveyServiceImpl implements CommandSurveyService {

    private final CommandSurveyMapper commandSurveyMapper;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyTagService surveyTagService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public long insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto, MultipartFile surveyImage) {

        String saveFilePath = null;
        try {
            saveFilePath = FileUploadUtil.uploadFile(surveyImage);
        } catch (IOException e) {
            throw new NotAcceptableFileException();
        }
        surveyInfoCreateDto.setSurveyImagePath(saveFilePath);

        commandSurveyMapper.insertSurveyInfo(surveyInfoCreateDto);

        long surveyNo = surveyInfoCreateDto.getSurveyNo();

        surveyInfoCreateDto.getSurveyTags().forEach((tagNo) ->
            surveyTagService.insertSurveyTag(
                new SurveyTagCreateDto(surveyNo, tagNo)
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
}

