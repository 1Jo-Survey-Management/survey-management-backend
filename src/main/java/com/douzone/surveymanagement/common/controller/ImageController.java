package com.douzone.surveymanagement.common.controller;

import com.douzone.surveymanagement.common.utils.ImageUtil;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 설문에 대한 이미지를 byte[] 로 반환하는 API 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문에 대한 이미지를 byte[]로 반환 합니다.
     * @author : 강명관
     */
    @GetMapping("/surveys/{surveyNo}")
    public ResponseEntity<byte[]> surveyImageDisplay(@PathVariable(value = "surveyNo") long surveyNo) {

        String surveyImagePath = querySurveyService.findSurveyImageBySurveyNo(surveyNo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ImageUtil.getExtensionMediaTypeByFileName(surveyImagePath));
        return new ResponseEntity<>(ImageUtil.getImageByteArray(surveyImagePath), headers, HttpStatus.OK);
    }

    /**
     * 유저에 대한 이미지를 byte[] 로 반환하는 API 입니다.
     *
     * @param userNo 유저 번호
     * @return 설문에 대한 이미지를 byte[]로 반환 합니다.
     * @author : 강명관
     */
    @GetMapping("/users/{userNo}")
    public ResponseEntity<byte[]> userImageDisplay(@PathVariable(value = "userNo") long userNo) {

        String userImagePath = userService.findUserImageByUserNo(userNo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ImageUtil.getExtensionMediaTypeByFileName(userImagePath));
        return new ResponseEntity<>(ImageUtil.getImageByteArray(userImagePath), headers,
            HttpStatus.OK);
    }


}
