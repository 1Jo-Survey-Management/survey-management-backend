package com.douzone.surveymanagement.common.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.common.utils.S3PreSignedUrlGenerator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이미지에 대한 API들을 정의해놓은 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3PreSignedUrlGenerator s3PreSignedUrlGenerator;

    /**
     * S3 preSignedURL을 생서해서 반환하는 API 입니다.
     *
     * @param fileName 업로드할 파일 이름
     * @return S3 PreSignedURL
     * @author : 강명관
     */
    @Operation(
        summary = "S3 사전 서명된 URL 생성",
        description = "주어진 파일 이름에 대해 S3 사전 서명된 URL을 생성하여 반환합니다."
    )
    @GetMapping("/presigned-url")
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
