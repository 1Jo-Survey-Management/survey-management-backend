package com.douzone.surveymanagement.statistics.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.service.SelectService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SelectController {

    private SelectService selectService;
    public SelectController(SelectService selectService) {
        this.selectService = selectService;
    }

    @GetMapping("/result")
    public ResponseEntity<List<SelectDto>> findSelectionList(@RequestParam(value = "surveyno") int surveyNo,@RequestParam(value = "questionno") int surveyQuestionNo){


        System.out.println("들어옴" + surveyNo);
        System.out.println("들어옴" + surveyQuestionNo);


    List<SelectDto> selectList = selectService.readSelection(surveyNo, surveyQuestionNo);

        System.out.println("selectList : " + selectList.get(0).getSurveyNo());

        return null;
    }

    @GetMapping("/resultall")
    public ResponseEntity<CommonResponse>  findSelectionListAll(@RequestParam(value = "surveyno") int surveyNo){

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);

        System.out.println("resultAll 들어옴 : " + surveyNo);
        System.out.println("selectList : " + selectList.get(0).getUserNickname());

        return ResponseEntity
                .ok()
                .body(CommonResponse.successOf(selectList));

    }
}
