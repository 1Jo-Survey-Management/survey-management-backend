package com.douzone.surveymanagement.surveyattend.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 설문 참여 제출 관련 데이터를 나타내는 'DTO' 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 */
@ToString
@Getter
@Setter
public class SurveyAttendSubmitDTO {
    private long userNo;
    private long surveyNo;
    private long surveyQuestionNo;
    private int questionTypeNo;
    private long selectionNo;
    private String surveyQuestionTitle;
    private String selectionValue;
    private String surveySubjectiveAnswer;
    private long surveyAttendNo;
    private long surveyAnswerNo;
    private long surveyAnswerSelectionNo;
}
