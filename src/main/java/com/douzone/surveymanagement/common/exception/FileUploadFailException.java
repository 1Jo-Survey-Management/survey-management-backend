package com.douzone.surveymanagement.common.exception;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class FileUploadFailException extends RuntimeException {

    private static final String MESSAGE = "파일 업로드에 실패하였습니다.";

    public FileUploadFailException() {
        super(MESSAGE);
    }
}
