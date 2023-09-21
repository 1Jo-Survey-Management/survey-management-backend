package com.douzone.surveymanagement.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    /**
     * 파일을 업로드하고 이미지 정보를 업데이트합니다.
     *
     * @param userNo 유저 번호
     * @param file   업로드할 파일
     * @return 업로드된 파일의 저장 경로
     * @throws IOException 파일 업로드 중 발생한 예외
     */
    String uploadImageAndUpdateUser(Long userNo, MultipartFile file) throws IOException;
}
