package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommonResponse<SurveyDetailsDto>> surveyDetails(
        @PathVariable(value = "surveyNo") long surveyNo
    ) {
        SurveyDetailsDto surveyDetails = querySurveyService.findSurveyDetails(surveyNo);
        return ResponseEntity.ok(CommonResponse.successOf(surveyDetails));
    }
  
    /**
     * 이번 주 내에 등록된 설문 중 참여자가 많은 설문 10개를 가져오는 API입니다.
     *
     * @return 인기 설문 10개
     *
     */

    @GetMapping("/weekly")
    public ResponseEntity<List<SurveyDetailInfoDto>> weeklySurveyList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> weeklySurvey = querySurveyService.readWeeklySurvey(userNo);
        return ResponseEntity.ok(weeklySurvey);
    }

    /**
     * 최근 등록된 설문 10개를 가져오는 API 입니다.
     *
     * @return 최근 등록 설문 10개
     *
     */
    @GetMapping("/recent")
    public ResponseEntity<List<SurveyDetailInfoDto>> recentSurveyList (@AuthenticationPrincipal CustomUserDetails userDetails) {
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> recentSurvey = querySurveyService.readRecentSurvey(userNo);
        return ResponseEntity.ok(recentSurvey);
    }

    /**
     * 최근에 마감된 순서로 설문 10개를 가져오는 API입니다.
     *
     * @return 최근 마감 설문 10개
     *
     */
    @GetMapping("/closing")
    public ResponseEntity<List<SurveyDetailInfoDto>> closingSurveyList (@AuthenticationPrincipal CustomUserDetails userDetails) {
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> closingSurvey = querySurveyService.readClosingSurvey(userNo);
        return ResponseEntity.ok(closingSurvey);
    }

    /**
     * 게시, 마감된 설문 전체를 가져오는 API 입니다.
     *
     * @param page
     * @return 전체 설문
     *
     */
    @GetMapping("/surveyall")
    public ResponseEntity<List<SurveyDetailInfoDto>> getAllSurvey(@RequestParam("page") int page, @AuthenticationPrincipal CustomUserDetails userDetails){
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> allSurvey = querySurveyService.getSurveyAll(page, userNo);
        return ResponseEntity.ok(allSurvey);
    }

    /**
     * 검색에서 마감을 선택할 시 마감된 설문을 20개씩 끊어 가져오는 API입니다.
     *
     * @param page
     * @return 마감 설문 20개
     *
     */
    @GetMapping("/select-closing")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectClosingSurveyList(@RequestParam("page") int page,@AuthenticationPrincipal CustomUserDetails userDetails){
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> closeSurvey = querySurveyService.selectClosing(page, userNo);
        return ResponseEntity.ok(closeSurvey);
    }

    /**
     * 검색에서 진행을 선택 시 진행중인 설문을 20개씩 가져오는 API입니다.
     *
     * @param page
     * @return 진행중인 설문 20개
     *
     */
    @GetMapping("/select-post")
    public ResponseEntity<List<SurveyDetailInfoDto>> selectPostSurveyList(@RequestParam("page") int page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userNo = userDetails.getUserNo();
        List<SurveyDetailInfoDto> postSurvey = querySurveyService.selectPost(page, userNo);
        return ResponseEntity.ok(postSurvey);
    }
}
