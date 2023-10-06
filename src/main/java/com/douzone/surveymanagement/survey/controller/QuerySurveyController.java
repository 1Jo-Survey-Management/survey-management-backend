package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.survey.domain.Survey;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class QuerySurveyController {

    private QuerySurveyService querySurveyService;

    public QuerySurveyController(QuerySurveyService querySurveyService) {
        this.querySurveyService = querySurveyService;
    }

    // 인기설문 조회 API

    @GetMapping("/weekly")
    public ResponseEntity<List<SurveyDetailInfoDto>> weeklySurveyList() {
        List<SurveyDetailInfoDto> weeklySurvey = querySurveyService.readWeeklySurvey();
        return ResponseEntity.ok(weeklySurvey);
    }

    // 최근 설문 조희 API
    @GetMapping("/recent")
    public ResponseEntity<List<SurveyDetailInfoDto>> recentSurveyList () {
        List<SurveyDetailInfoDto> recentSurvey = querySurveyService.readRecentSurvey();
        return ResponseEntity.ok(recentSurvey);
    }

    // 최근 마감 설문 조회 API
    @GetMapping("/closing")
    public ResponseEntity<List<SurveyDetailInfoDto>> closingSurveyList () {
        List<SurveyDetailInfoDto> closingSurvey = querySurveyService.readClosingSurvey();
        return ResponseEntity.ok(closingSurvey);
    }

    // 전체 설문 조회
    @GetMapping("/surveyall")
    public ResponseEntity<List<SurveyDetailInfoDto>> getAllSurvey(){
        List<SurveyDetailInfoDto> allSurvey = querySurveyService.getSurveyAll();
        return ResponseEntity.ok(allSurvey);
    }

    @GetMapping("/select-closing")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectClosingSurveyList(){
        List<SurveyDetailInfoDto> closeSurvey = querySurveyService.selectClosing();
        return ResponseEntity.ok(closeSurvey);
    }

    @GetMapping("/select-post")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectPostSurveyList() {
        List<SurveyDetailInfoDto> postSurvey = querySurveyService.selectPost();
        return ResponseEntity.ok(postSurvey);
    }

}



