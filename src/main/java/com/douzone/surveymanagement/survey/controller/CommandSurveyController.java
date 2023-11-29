package com.douzone.surveymanagement.survey.controller;

import com.douzone.surveymanagement.common.annotation.RequiredUser;
import com.douzone.surveymanagement.common.annotation.S3DeleteObject;
import com.douzone.surveymanagement.common.exception.BadRequestException;
import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.survey.dto.request.SurveyCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyUpdateDto;
import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param surveyCreateDto 설문을 작성하기 위한 모든 정보를 갖고있는 Dto 입니다.
     * @return 성공적으로 저장되었을 경우 CREATED 201 을 응답합니다.
     * @author : 강명관
     */
    @Operation(
        summary = "설문 생성",
        description = "새로운 설문을 등록합니다. 설문 생성에 필요한 모든 정보가 포함된 DTO를 사용합니다."
    )
    @RequiredUser
    @PostMapping
    public ResponseEntity<CommonResponse<String>> surveyCreate(
        @RequestBody SurveyCreateDto surveyCreateDto,
        @AuthenticationPrincipal CustomUserDetails userDetails
        ) {

        surveyCreateDto.getSurveyInfoCreateDto().setUserNo(userDetails.getUserNo());

        commandSurveyService.insertSurvey(
            surveyCreateDto.getSurveyInfoCreateDto(),
            surveyCreateDto.getSurveyQuestionCreateDtoList()
        );

        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.CREATED);
    }

    /**
     * 설문을 수정하는 API 입니다.
     *
     * @param surveyUpdateDto 설문의 수정에 필요한 데이터
     * @param userDetails 인가된 사용자
     * @return 공용 응답객체
     * @author : 강명관
     */
    @Operation(
        summary = "설문 수정",
        description = "기존에 생성된 설문을 수정합니다. 수정에 필요한 데이터를 포함한 DTO를 사용합니다."
    )
    @RequiredUser
    @S3DeleteObject
    @PutMapping
    public ResponseEntity<CommonResponse<String>> surveyUpdate(
        @RequestBody SurveyUpdateDto surveyUpdateDto,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean surveyCreatedByUser = querySurveyService.isSurveyCreatedByUser(
            userDetails.getUserNo(),
            surveyUpdateDto.getSurveyInfoUpdateDto().getSurveyNo()
        );

        if (!surveyCreatedByUser) {
            throw new BadRequestException("선택한 설문을 수정할 수 없습니다.");
        }

        commandSurveyService.updateSurvey(
            surveyUpdateDto.getSurveyInfoUpdateDto(),
            surveyUpdateDto.getSurveyQuestionCreateDtoList()
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
    @Operation(
        summary = "설문 상태 변경 (게시)",
        description = "진행 중인 설문의 상태를 게시 상태로 변경합니다. 설문 번호를 기반으로 상태를 변경합니다."
    )
    @RequiredUser
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
