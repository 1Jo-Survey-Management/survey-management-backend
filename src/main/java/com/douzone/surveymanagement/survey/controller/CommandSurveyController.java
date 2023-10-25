package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 설문을 등록하는 API를 정의한 Controller 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class CommandSurveyController {

    private final CommandSurveyService commandSurveyService;

    /**
     * 섦문을 등록하는 REST API 입니다.
     *
     * @param surveyInfoCreateDto 설문에 대한 정보를 담은 Dto
     * @param surveyQuestionCreateDtoList 설문 문항에 대한 정보를 담은 Dto
     * @param surveyImage 설문에 대한 대표 이미지 Multipart
     * @return 성공적으로 저장되었을 경우 CREATED 201 을 응답합니다.
     * @author : 강명관
     */
    @PostMapping
    public ResponseEntity<CommonResponse<String>> surveyCreate(
        @RequestPart SurveyInfoCreateDto surveyInfoCreateDto,
        @RequestPart List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
        @RequestPart MultipartFile surveyImage
        ) {

        /**
         * FIXME: 유저 번호 테스트 데이터
         */
        surveyInfoCreateDto.setUserNo(1L);

        commandSurveyService.insertSurvey(
            surveyInfoCreateDto,
            surveyQuestionCreateDtoList,
            surveyImage
        );

        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<String>> surveyUpdate(
        @RequestPart SurveyInfoUpdateDto surveyInfoUpdateDto,
        @RequestPart List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
        @RequestPart(required = false) MultipartFile surveyImage
    ) {

        /**
         * FIXME: 로그인 유저와 해당 설문 작성자가 같은지 확인하는 로직 추가
         */
        commandSurveyService.updateSurvey(
            surveyInfoUpdateDto,
            surveyImage,
            surveyQuestionCreateDtoList
        );

        return ResponseEntity.ok(CommonResponse.success());
    }
}
