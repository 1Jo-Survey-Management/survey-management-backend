package com.douzone.surveymanagement.mysurvey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import com.douzone.surveymanagement.mysurvey.service.impl.MySurveyServiceImpl;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MyPage Survey API 컨트롤러 클래스입니다.
 *
 * <p>마이페이지 설문목록 관련 API 엔드포인트를 처리합니다.</p>
 *
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/api/my-surveys")
@RequiredArgsConstructor
public class MySurveyController {

    private final MySurveyServiceImpl mySurveyServiceImpl;

    /**
     * 사용자가 작성한 설문 목록을 가져옵니다.
     *
     * @param userDetails 로그인 된 사용자 번호
     * @return 설문 목록과 상태 정보를 포함한 응답
     */
    @GetMapping("/write-surveys")
    @Operation(summary = "사용자가 작성한 설문 목록 가져오기", description = "로그인한 사용자가 작성한 설문 목록을 가져옵니다.")
    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectMySurvey(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<MySurveyDTO> myWriteSurveys = mySurveyServiceImpl.selectMySurveysWithSorting(userDetails.getUserNo());


        return ResponseEntity.ok(CommonResponse.successOf(myWriteSurveys));
    }


    /**
     * 사용자가 참여한 설문 목록을 가져옵니다.
     *
     * @param userDetails 로그인 된 사용자 번호
     * @return 설문 목록과 상태 정보를 포함한 응답
     */
    @GetMapping("/attend-surveys")
    @Operation(summary = "사용자가 참여한 설문 목록 가져오기", description = "로그인한 사용자가 참여한 설문 목록을 가져옵니다.")
    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectAttendSurvey(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<MySurveyDTO> myAttendSurveys = mySurveyServiceImpl.selectMyParticipatedSurveys(userDetails.getUserNo());

        return ResponseEntity.ok(CommonResponse.successOf(myAttendSurveys));
    }

    @PutMapping("/update-write-surveys")
    @Operation(summary = "사용자가 작성한 설문 수정하기", description = "로그인한 사용자가 작성한 특정 설문을 수정합니다.")
    public ResponseEntity<CommonResponse> updateMySurvey(
            @RequestBody MySurveyDTO mySurveyDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        mySurveyDTO.setUserNo(userDetails.getUserNo());
        boolean isDeleted = mySurveyServiceImpl.deleteMySurveyInProgress(mySurveyDTO);
        if (isDeleted) {
            return ResponseEntity.ok(CommonResponse.successOf("Survey deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.error(ErrorResponse.of("Failed to delete survey")));
        }
    }
}