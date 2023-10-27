package com.douzone.surveymanagement.surveyattend.dto.request;

import lombok.Getter;
import lombok.ToString;

/**
 * 설문 참여 관련 데이터를 나타내는 'DTO' 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 */
@ToString
@Getter
public class SurveyAttendDTO {
    private String surveyTitle;
    private String surveyImage;
    private long surveyQuestionNo;
    private long surveyNo;
    private int questionTypeNo;
    private String surveyQuestionTitle;
    private String surveyQuestionDescription;
    private boolean isRequired;
    private long selectionNo;
    private long surveyQuestionMoveNo;
    private String selectionValue;
    private boolean isMovable;
    private boolean isEndOfSurvey;
    private long userNo;
    private long surveyAttendNo;
    private String surveySubjectiveAnswer;
    private long surveyAnswerNo;

}
