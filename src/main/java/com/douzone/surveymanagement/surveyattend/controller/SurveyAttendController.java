package com.douzone.surveymanagement.surveyattend.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendDTO;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendSubmitDTO;
import com.douzone.surveymanagement.surveyattend.exception.SurveyAttendException;
import com.douzone.surveymanagement.surveyattend.service.impl.SurveyAttendServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 설문 참여 관련 작업을 담당하는 컨트롤러입니다.
 * 참여를 위한 설문에 대한 CRUD 작업을 처리합니다.
 *
 * @author : 박창우
 * @since : 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/for-attend/surveys")
@RequiredArgsConstructor
public class SurveyAttendController {

    private final SurveyAttendServiceImpl surveyAttendServiceImpl;

    /**
     * 설문에 참여하기 위한 데이터를 가져옵니다.
     *
     * @return 설문 데이터 목록을 담은 ResponseEntity
     */
    @GetMapping("/survey-data")
    @Operation(summary = "설문 데이터 가져오기", description = "설문 참여를 위한 데이터를 조회합니다.")
    public ResponseEntity<CommonResponse> getSurveyData() {
        List<SurveyAttendDTO> surveyData = surveyAttendServiceImpl.selectSurveyAttendData();
        return ResponseEntity
                .ok()
                .body(CommonResponse.successOf(surveyData));
    }

    /**
     * 사용자의 설문 응답을 저장합니다.
     * 이에는 주요 설문 참여 데이터와 주관식/객관식 답변이 포함됩니다.
     *
     * @param surveyAttendSubmitDTOList 사용자의 설문 응답을 담고 있는 데이터 전송 객체
     * @return 작업 상태를 담은 ResponseEntity
     */
    @PostMapping("/save-responses")
    @Operation(summary = "사용자 응답 저장하기", description = "사용자의 설문 응답을 저장합니다. 주관식 및 객관식 답변을 포함합니다.")
    public ResponseEntity<CommonResponse> saveSurveyResponses(@RequestBody List<SurveyAttendSubmitDTO> surveyAttendSubmitDTOList) {

        try {
            surveyAttendServiceImpl.saveSurveyAndAnswers(surveyAttendSubmitDTOList);
            return ResponseEntity.ok().body(CommonResponse.success());
        } catch (SurveyAttendException e) {
            log.error("설문 응답 저장 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(CommonResponse.fail());
        } catch (Exception e) {
            log.error("설문 응답 저장 중 알 수 없는 오류 발생", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.error(ErrorResponse.of("ERROR_SAVING_SURVEY")));
        }
    }

    /**
     * 주어진 설문 번호에 대한 마감일을 클라이언트에 제공합니다.
     *
     * @param surveyNo 설문 번호
     * @return 마감일 정보를 담은 ResponseEntity
     */
    @GetMapping("/closing-time/{surveyNo}")
    @Operation(summary = "설문 마감일 조회하기", description = "설문 번호에 따른 마감일을 조회합니다.")
    public ResponseEntity<CommonResponse> getSurveyClosingTime(@PathVariable long surveyNo) {
        try {
            LocalDateTime closingTime = surveyAttendServiceImpl.getSurveyClosingTime(surveyNo);
            return ResponseEntity.ok().body(CommonResponse.successOf(closingTime));
        } catch (Exception e) {
            log.error("마감일 정보 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.error(ErrorResponse.of("ERROR_RETRIEVING_CLOSING_TIME")));
        }
    }
}
