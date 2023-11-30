package com.douzone.surveymanagement.statistics.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.service.SelectService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey")
public class SelectionController {

    private final SelectService selectService;

    @GetMapping("/resultall")
    @Operation(summary = "모든 선택 항목 조회", description = "주어진 설문 번호에 대해 모든 통계의 결과를 조회합니다.")
    public ResponseEntity<CommonResponse> findSelectionListAll(
        @RequestParam(value = "surveyno") long surveyNo
    ) {

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);

        if (!selectList.isEmpty()) {
            int rangeCheck = selectList.get(0).getOpenStatusNo();

            if (rangeCheck == 3) {
                return null;
            }

            CommonResponse commonResponse = CommonResponse.successOf(selectList);

            return ResponseEntity
                .ok()
                .body(commonResponse);
        } else {
            return null;
        }

    }

    @GetMapping("/resultall/nonMember")
    @Operation(summary = "비회원을 위한 모든 선택 항목 조회", description = "비회원을 위해 주어진 설문 번호에 대한 모든 통계의 결과를 조회합니다. 특정 공개 범위에 따라 접근이 제한될 수 있습니다.")
    public ResponseEntity<CommonResponse> nonMemeberFindSelectionListAll(
        @RequestParam(value = "surveyno") long surveyNo) {

        List<SelectDto> selectList = selectService.readSelectionAll(surveyNo);

        if (!selectList.isEmpty()) {
            int rangeCheck = selectList.get(0).getOpenStatusNo();

            if (rangeCheck == 2 || rangeCheck == 3) {
                return null;
            }

            CommonResponse commonResponse = CommonResponse.successOf(selectList);

            return ResponseEntity
                .ok()
                .body(commonResponse);
        } else {
            return null;
        }

    }


}
