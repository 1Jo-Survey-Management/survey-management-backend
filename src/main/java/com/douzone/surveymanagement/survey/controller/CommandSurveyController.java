package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.request.SurveyCreateDto;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@RestController
@RequestMapping("/v1/survey")
@RequiredArgsConstructor
public class CommandSurveyController {

    private final CommandSurveyService commandSurveyService;

    /**
     * 설문을 등록하는 메서드 입니다.
     *
     * @param surveyCreateDto 설문에 대한 모든 정보를 담고있는 dto 입니다.
     * @return Http Status: 201
     * @author : 강명관
     */
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<String>> surveyRegister(
        @Valid @RequestBody SurveyCreateDto surveyCreateDto
    ) {
        commandSurveyService.insertSurvey(surveyCreateDto);
        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.CREATED);
    }
}
