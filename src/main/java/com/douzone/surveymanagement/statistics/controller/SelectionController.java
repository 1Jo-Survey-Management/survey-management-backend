package com.douzone.surveymanagement.statistics.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.service.SelectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey")
public class SelectionController {

    private final SelectService selectService;

    @GetMapping("/resultall")
    public ResponseEntity<CommonResponse>  findSelectionListAll(@RequestParam(value = "surveyno") long surveyNo){

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);

        if(!selectList.isEmpty()){
            int rangeCheck = selectList.get(0).getOpenStatusNo();

            if(rangeCheck ==3){
                return null;
            }

            CommonResponse commonResponse = CommonResponse.successOf(selectList);

            return ResponseEntity
                    .ok()
                    .body(commonResponse);
        }else{
            return null;
        }

    }

    @GetMapping("/resultall/nonMember")
    public ResponseEntity<CommonResponse>  nonMemeberFindSelectionListAll(
            @RequestParam(value = "surveyno") long surveyNo){

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);

        if(!selectList.isEmpty()){
            int rangeCheck = selectList.get(0).getOpenStatusNo();

            if(rangeCheck==2 || rangeCheck ==3){
                return null;
            }

            CommonResponse commonResponse = CommonResponse.successOf(selectList);

            return ResponseEntity
                    .ok()
                    .body(commonResponse);
        }else{
            return null;
        }

    }


}
