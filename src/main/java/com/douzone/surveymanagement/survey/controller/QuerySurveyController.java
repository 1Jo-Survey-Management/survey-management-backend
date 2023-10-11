package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.survey.domain.Survey;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 설문 목록 조회 API를 만든 Controller 입니다.
 *
 * @author : 엄아진
 * @since : 1.0
 *
 *
 */

@RestController
@RequestMapping("/surveys")
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
    public ResponseEntity<List<SurveyDetailInfoDto>> getAllSurvey(@RequestParam("page") int page){
        List<SurveyDetailInfoDto> allSurvey = querySurveyService.getSurveyAll(page);
        return ResponseEntity.ok(allSurvey);
    }

    @GetMapping("/select-closing")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectClosingSurveyList(@RequestParam("page") int page){
        List<SurveyDetailInfoDto> closeSurvey = querySurveyService.selectClosing(page);
        return ResponseEntity.ok(closeSurvey);
    }

    @GetMapping("/select-post")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectPostSurveyList(@RequestParam("page") int page) {
        List<SurveyDetailInfoDto> postSurvey = querySurveyService.selectPost(page);
        return ResponseEntity.ok(postSurvey);
    }

}



