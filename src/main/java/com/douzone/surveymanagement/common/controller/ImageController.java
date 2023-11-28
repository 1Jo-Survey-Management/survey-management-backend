package com.douzone.surveymanagement.common.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.common.utils.ImageUtil;
import com.douzone.surveymanagement.common.utils.S3ObjectDeleter;
import com.douzone.surveymanagement.common.utils.S3PreSignedUrlGenerator;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import com.douzone.surveymanagement.user.service.UserService;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final QuerySurveyService querySurveyService;
    private final UserService userService;
    private final S3PreSignedUrlGenerator s3PreSignedUrlGenerator;

    private final S3ObjectDeleter s3ObjectDeleter;

    /**
     * 설문에 대한 이미지를 byte[] 로 반환하는 API 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문에 대한 이미지를 byte[]로 반환 합니다.
     * @author : 강명관
     */
    @GetMapping("/surveys/{surveyNo}")
    @Operation(summary = "설문 이미지 표시", description = "주어진 설문 번호에 해당하는 이미지를 byte 배열로 반환합니다.")
    public ResponseEntity<byte[]> surveyImageDisplay(
        @PathVariable(value = "surveyNo") long surveyNo) {

        String surveyImagePath = querySurveyService.findSurveyImageBySurveyNo(surveyNo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ImageUtil.getExtensionMediaTypeByFileName(surveyImagePath));
        return new ResponseEntity<>(ImageUtil.getImageByteArray(surveyImagePath), headers,
            HttpStatus.OK);
    }

    @GetMapping("/user-image")
    @Operation(summary = "사용자 이미지 표시", description = "로그인한 사용자의 프로필 이미지를 byte 배열로 반환합니다.")
    public ResponseEntity<byte[]> userImageDisplay(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userImagePath = userService.findUserImageByUserNo(userDetails.getUserNo());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ImageUtil.getExtensionMediaTypeByFileName(userImagePath));
        return new ResponseEntity<>(ImageUtil.getImageByteArray(userImagePath), headers,
            HttpStatus.OK);
    }

    @GetMapping("/presigned-url")
    @Operation(summary = "S3 사전 서명된 URL 생성", description = "주어진 파일 이름에 대해 S3 사전 서명된 URL을 생성하여 반환합니다.")
    public ResponseEntity<CommonResponse> S3GeneratePreSignedURL(
        @RequestParam String fileName
    ) {
        try {
            String preSignedUrl = s3PreSignedUrlGenerator.getPreSignedUrl(fileName);
            return ResponseEntity.ok(CommonResponse.successOf(preSignedUrl));
        } catch (Exception e) {
            log.error("Error generating Pre-Signed URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.error(ErrorResponse.of("Error generating Pre-Signed URL")));
        }
    }
}
