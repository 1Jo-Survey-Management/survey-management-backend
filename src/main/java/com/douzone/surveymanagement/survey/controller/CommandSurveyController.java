package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.exception.BadRequestException;
import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 설문을 등록하는 API를 정의한 Controller 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class CommandSurveyController {

    private final CommandSurveyService commandSurveyService;
    private final QuerySurveyService querySurveyService;

    /**
     * 섦문을 등록하는 REST API 입니다.
     *
     * @param surveyInfoCreateDto 설문에 대한 정보를 담은 Dto
     * @param surveyQuestionCreateDtoList 설문 문항에 대한 정보를 담은 Dto
     * @param surveyImage 설문에 대한 대표 이미지 Multipart
     * @return 성공적으로 저장되었을 경우 CREATED 201 을 응답합니다.
     * @author : 강명관
     */
    @PostMapping
    public ResponseEntity<CommonResponse<String>> surveyCreate(
        @RequestPart SurveyInfoCreateDto surveyInfoCreateDto,
        @RequestPart List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
        @RequestPart MultipartFile surveyImage,
        @AuthenticationPrincipal CustomUserDetails userDetails
        ) {

        surveyInfoCreateDto.setUserNo(userDetails.getUserNo());

        commandSurveyService.insertSurvey(
            surveyInfoCreateDto,
            surveyQuestionCreateDtoList,
            surveyImage
        );

        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.CREATED);
    }

    /**
     * 설문을 수정하는 API 입니다.
     *
     * @param surveyInfoUpdateDto 수정할 설문의 정보
     * @param surveyQuestionCreateDtoList 수정된 설문 문항, 선택지 리스트
     * @param surveyImage 설문 이미지
     * @param userDetails 인가된 사용자
     * @return 공용 응답객체
     * @author : 강명관
     */
    @PutMapping
    public ResponseEntity<CommonResponse<String>> surveyUpdate(
        @RequestPart SurveyInfoUpdateDto surveyInfoUpdateDto,
        @RequestPart List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
        @RequestPart(required = false) MultipartFile surveyImage,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        boolean surveyCreatedByUser = querySurveyService.isSurveyCreatedByUser(
            userDetails.getUserNo(),
            surveyInfoUpdateDto.getSurveyNo()
        );

        if (!surveyCreatedByUser) {
            throw new BadRequestException("선택한 설문을 수정할 수 없습니다.");
        }

        commandSurveyService.updateSurvey(
            surveyInfoUpdateDto,
            surveyImage,
            surveyQuestionCreateDtoList
        );

        return ResponseEntity.ok(CommonResponse.success());
    }

    /**
     * 작성된 설문의 상태를 게시하는 API 입니다.
     *
     * @param surveyNo 설문 번호
     * @param userDetails 인가된 사용자
     * @return 공용응답 객체
     * @author : 강명관
     */
    @PutMapping("/{surveyNo}/post")
    public ResponseEntity<CommonResponse<String>> surveyStatusToPostFromInProgress(
        @PathVariable(value = "surveyNo") long surveyNo,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        if (!querySurveyService.isSurveyCreatedByUser(
            userDetails.getUserNo(),
            surveyNo
        )) {
            throw new BadRequestException("선택한 설문을 삭제할 수 없습니다.");
        }

        if (!commandSurveyService.updateSurveyStatusToPostInProgress(surveyNo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail());
        }

        return ResponseEntity.ok(CommonResponse.success());
    }
}
