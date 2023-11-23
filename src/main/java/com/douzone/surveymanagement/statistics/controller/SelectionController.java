package com.douzone.surveymanagement.statistics.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.service.SelectService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/resultall")
    public ResponseEntity<CommonResponse>  findSelectionListAll(@RequestParam(value = "surveyno") long surveyNo){

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);
        CommonResponse commonResponse = CommonResponse.successOf(selectList);

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }

    @GetMapping("/nonMember/resultall")
    public ResponseEntity<CommonResponse>  nonMemberFindSelectionListAll(@RequestParam(value = "surveyno") long surveyNo){

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);
        CommonResponse commonResponse = CommonResponse.successOf(selectList);

        return ResponseEntity
                .ok()
                .body(commonResponse);
    }
}
