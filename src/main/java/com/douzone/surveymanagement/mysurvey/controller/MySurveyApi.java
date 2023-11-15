package com.douzone.surveymanagement.mysurvey.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import com.douzone.surveymanagement.mysurvey.service.MySurveyService;
import com.douzone.surveymanagement.mysurvey.service.impl.MySurveyServiceImpl;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
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
public class MySurveyApi {

    private final MySurveyService mySurveyServiceImpl;


//    /**
//     * 사용자가 작성한 설문 목록을 가져옵니다.
//     *
//     * @param userNo 사용자 번호
//     * @return 설문 목록과 상태 정보를 포함한 응답
//     */
//    @GetMapping("/{userNo}/write-surveys")
//    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectMySurvey(@PathVariable long userNo ) {
//        List<MySurveyDTO> myWriteSurveys = mySurveyServiceImpl.selectMySurveysWithSorting(userNo);
//        return ResponseEntity
//                .ok()
//                .body(CommonResponse.successOf(myWriteSurveys));
//    }

    /**
     * 사용자가 작성한 설문 목록을 가져옵니다.
     *
     * @param userDetails 로그인 된 사용자 번호
     * @return 설문 목록과 상태 정보를 포함한 응답
     */
    @GetMapping("/write-surveys")
    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectMySurvey(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<MySurveyDTO> myWriteSurveys = mySurveyServiceImpl.selectMySurveysWithSorting(userDetails.getUserNo());

        myWriteSurveys.forEach(survey -> System.out.println("write 객체 로깅: " + survey.toString()));


        return ResponseEntity.ok(CommonResponse.successOf(myWriteSurveys));
    }

//    /**
//     * 사용자가 참여한 설문 목록을 가져옵니다.
//     *
//     * @param userNo 사용자 번호
//     * @return 설문 목록과 상태 정보를 포함한 응답
//     */
//    @GetMapping("/{userNo}/attend-surveys")
//    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectAttendSurvey(@PathVariable long userNo) {
//        List<MySurveyDTO> myAttendSurveys = mySurveyServiceImpl.selectMyParticipatedSurveys(userNo);
//        return ResponseEntity
//                .ok()
//                .body(CommonResponse.successOf(myAttendSurveys));
//    }

    /**
     * 사용자가 참여한 설문 목록을 가져옵니다.
     *
     * @param userDetails 로그인 된 사용자 번호
     * @return 설문 목록과 상태 정보를 포함한 응답
     */
    @GetMapping("/attend-surveys")
    public ResponseEntity<CommonResponse<List<MySurveyDTO>>> selectAttendSurvey(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<MySurveyDTO> myAttendSurveys = mySurveyServiceImpl.selectMyParticipatedSurveys(userDetails.getUserNo());

        myAttendSurveys.forEach(survey -> System.out.println("attend 객체 로깅: " + survey.toString()));

        return ResponseEntity.ok(CommonResponse.successOf(myAttendSurveys));
    }

//    /**
//     * 사용자가 작성 중인 설문 삭제 엔드포인트입니다.
//     *
//     * @param mySurveyDTO MySurveyDTO 객체
//     * @return 응답 엔터티
//     */
//    @PutMapping("/update-write-surveys")
//    public ResponseEntity<CommonResponse> updateMySurvey(
//            @RequestBody MySurveyDTO mySurveyDTO) {
//        boolean isDeleted = mySurveyServiceImpl.deleteMySurveyInProgress(mySurveyDTO);
//        if (isDeleted) {
//            return ResponseEntity
//                    .ok()
//                    .body(CommonResponse.successOf("Survey deleted successfully"));
//        } else {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(CommonResponse.<String>error(ErrorResponse.of("Failed to delete survey")));
//        }
//    }
    @PutMapping("/update-write-surveys")
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