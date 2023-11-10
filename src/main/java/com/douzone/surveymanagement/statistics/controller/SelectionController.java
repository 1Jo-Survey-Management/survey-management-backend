package com.douzone.surveymanagement.statistics.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.service.SelectService;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
public class SelectionController {

    private SelectService selectService;
    public SelectionController(SelectService selectService) {
        this.selectService = selectService;
    }

//    @GetMapping("/result")
//    public ResponseEntity<List<SelectDto>> findSelectionList(@RequestParam(value = "surveyno") int surveyNo,@RequestParam(value = "questionno") int surveyQuestionNo){
//
//
//        System.out.println("들어옴" + surveyNo);
//        System.out.println("들어옴" + surveyQuestionNo);
//
//
//    List<SelectDto> selectList = selectService.readSelection(surveyNo,surveyQuestionNo);
//
//        System.out.println("selectList : " + selectList.get(0).getSurveyNo());
//
//        return null;
//    }

    @GetMapping("/resultall")
    public ResponseEntity<CommonResponse>  findSelectionListAll(@RequestParam(value = "surveyno") int surveyNo){

        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
        HttpHeaders headers = new HttpHeaders();

        if (authenticationCheck != null) {
            String accessToken = (String) authenticationCheck.getCredentials();
            System.out.println("토큰 확인 : " + accessToken);
            headers.add("Authorization", "Bearer whatth fuck");
        } else {
            System.out.println("토큰이 null : " + authenticationCheck);
        }

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);
        CommonResponse commonResponse = CommonResponse.successOf(selectList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(commonResponse);
    }
}