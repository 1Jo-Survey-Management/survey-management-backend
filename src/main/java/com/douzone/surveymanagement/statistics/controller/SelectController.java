package com.douzone.surveymanagement.statistics.controller;

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
    public SelectController(SelectService selectService) {
        this.selectService = selectService;
    }

    private SelectService selectService;

    @GetMapping("/result")
    public ResponseEntity<List<SelectDto>> findSelectionList(@RequestParam(value = "surveyno") int surveyNo,@RequestParam(value = "questionno") int surveyQuestionNo){
       List<SelectDto> selectList = selectService.readSelection(surveyNo, surveyQuestionNo);
        return ResponseEntity.ok(selectList);
    }
}
