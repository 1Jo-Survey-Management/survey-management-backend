package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class QuerySurveyController {

    private final QuerySurveyService querySurveyService;

    @GetMapping("/{surveyNo}")
    public ResponseEntity surveyDetails(@PathVariable(value = "surveyNo") long surveyNo) {
        SurveyDetailsDto surveyDetails = querySurveyService.findSurveyDetails(surveyNo);
        return ResponseEntity.ok(CommonResponse.successOf(surveyDetails));
    }


}
