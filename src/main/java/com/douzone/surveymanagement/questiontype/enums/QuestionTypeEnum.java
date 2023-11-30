package com.douzone.surveymanagement.questiontype.enums;

import com.douzone.surveymanagement.common.exception.NotFoundElementException;

/**
 * 문항의 타입을 관리하기 위한 Enum 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public enum QuestionTypeEnum {

    SINGLE_SELECTION(1, "단일 선택형", true),
    MOVABLE_SINGLE_SELECTION(2, "선택시 문항이동 단일 선택형", true),
    MULTIPLE_SELECTION(3, "복수 선택형", true),
    SHORT_ANSWER(4, "주관식 단답형", false),
    SUBJECTIVE_ANSWER(5, "주관식 서술형", false),
    ;

    private final int questionTypeNo;
    private final String questionTypeName;
    private final boolean isSelection;

    QuestionTypeEnum(int questionTypeNo, String questionTypeName, boolean isSelection) {
        this.questionTypeNo = questionTypeNo;
        this.questionTypeName = questionTypeName;
        this.isSelection = isSelection;
    }

    public int getQuestionTypeNo() {
        return questionTypeNo;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public boolean isSelection() {
        return isSelection;
    }

    public static QuestionTypeEnum convertTo(int questionTypeNo) {
        for (QuestionTypeEnum type : values()) {
            if (type.getQuestionTypeNo() == questionTypeNo) {
                return type;
            }
        }
        throw new NotFoundElementException("No Such Question Type");
    }

}
