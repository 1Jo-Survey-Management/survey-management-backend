//package com.douzone.surveymanagement.survey.controller;
//
//import com.douzone.surveymanagement.common.response.CommonResponse;
//import com.douzone.surveymanagement.survey.domain.Survey;
//import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
//import com.douzone.surveymanagement.survey.service.QuerySurveyService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// *
// * 설문 목록 조회 API를 만든 Controller 입니다.
// *
// * @author : 엄아진
// * @since : 1.0
// *
// */
//
//@RestController
//@RequestMapping("/surveys")
//public class QuerySurveyController {
//
//    private QuerySurveyService querySurveyService;
//
//    public QuerySurveyController(QuerySurveyService querySurveyService) {
//        this.querySurveyService = querySurveyService;
//    }
//
//    /**
//     *
//     * 이번 주 내에 등록된 설문 중 참여자가 많은 설문 10개를 가져오는 API입니다.
//     *
//     * @return 인기 설문 10개
//     *
//     */
//
//    @GetMapping("/weekly")
//    public ResponseEntity<List<SurveyDetailInfoDto>> weeklySurveyList() {
//        List<SurveyDetailInfoDto> weeklySurvey = querySurveyService.readWeeklySurvey();
//        return ResponseEntity.ok(weeklySurvey);
//    }
//
//    /**
//     *
//     * 최근 등록된 설문 10개를 가져오는 API 입니다.
//     *
//     * @return 최근 등록 설문 10개
//     *
//     */
//    @GetMapping("/recent")
//    public ResponseEntity<List<SurveyDetailInfoDto>> recentSurveyList () {
//        List<SurveyDetailInfoDto> recentSurvey = querySurveyService.readRecentSurvey();
//        return ResponseEntity.ok(recentSurvey);
//    }
//
//    /**
//     *
//     * 최근에 마감된 순서로 설문 10개를 가져오는 API입니다.
//     *
//     * @return 최근 마감 설문 10개
//     *
//     */
//    @GetMapping("/closing")
//    public ResponseEntity<List<SurveyDetailInfoDto>> closingSurveyList () {
//        List<SurveyDetailInfoDto> closingSurvey = querySurveyService.readClosingSurvey();
//        return ResponseEntity.ok(closingSurvey);
//    }
//
//    /**
//     *
//     * 게시, 마감된 설문 전체를 가져오는 API 입니다.
//     *
//     * @param page
//     * @return 전체 설문
//     *
//     */
//    @GetMapping("/surveyall")
//    public ResponseEntity<List<SurveyDetailInfoDto>> getAllSurvey(@RequestParam("page") int page){
//        List<SurveyDetailInfoDto> allSurvey = querySurveyService.getSurveyAll(page);
//        return ResponseEntity.ok(allSurvey);
//    }
//
//    /**
//     *
//     * 검색에서 마감을 선택할 시 마감된 설문을 20개씩 끊어 가져오는 API입니다.
//     *
//     * @param page
//     * @return 마감 설문 20개
//     *
//     */
//    @GetMapping("/select-closing")
//    public ResponseEntity<List<SurveyDetailInfoDto>> selectClosingSurveyList(@RequestParam("page") int page){
//        List<SurveyDetailInfoDto> closeSurvey = querySurveyService.selectClosing(page);
//        return ResponseEntity.ok(closeSurvey);
//    }
//
//    /**
//     *
//     * 검색에서 진행을 선택 시 진행중인 설문을 20개씩 가져오는 API입니다.
//     *
//     * @param page
//     * @return 진행중인 설문 20개
//     *
//     */
//    @GetMapping("/select-post")
//    public ResponseEntity<List<SurveyDetailInfoDto>> selectPostSurveyList(@RequestParam("page") int page) {
//        List<SurveyDetailInfoDto> postSurvey = querySurveyService.selectPost(page);
//        return ResponseEntity.ok(postSurvey);
//    }
//
//}
//


