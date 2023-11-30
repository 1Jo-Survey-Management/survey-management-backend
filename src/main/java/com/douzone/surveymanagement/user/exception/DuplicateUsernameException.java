package com.douzone.surveymanagement.user.exception;

/**
 * 중복된 사용자 이름 예외 클래스입니다.
 * 이 예외는 이미 데이터베이스에 존재하는 사용자 이름을 추가하려고 할 때 발생합니다.
 *
 * @author : 박창우
 * @since : 1.0
 **/
public class DuplicateUsernameException extends RuntimeException {

    /**
     * 중복된 사용자 이름 예외 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public DuplicateUsernameException(String message) {
        super(message);
    }
}