package com.douzone.surveymanagement.surveyattend.exception;

/**
 * 설문 참여 관련 예외를 처리하는 클래스입니다.
 *
 * <p>
 * 이 예외 클래스는 설문 참여 과정에서 발생할 수 있는 예외 상황을 나타내기 위해 사용됩니다.
 * </p>
 *
 * @author : 박창우
 * @since : 1.0
 */
public class SurveyAttendException extends RuntimeException {

    /**
     * 'SurveyAttendException' 을 생성하고, 예외 메시지를 지정합니다.
     *
     * @param message 예외 상황을 설명하는 메시지입니다.
     */
    public SurveyAttendException(String message) {
        super(message);
    }
}
