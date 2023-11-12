package com.douzone.surveymanagement.surveystatus.enums;

import com.douzone.surveymanagement.common.exception.NotFoundElementException;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public enum SurveyStatusEnum {

    WRITING(1, "대기"),
    PROGRESS(2, "진행"),
    DEADLINE(3, "마감"),
    ;


    private final int surveyStatusNo;
    private final String surveyStatusName;

    SurveyStatusEnum(int surveyStatusNo, String surveyStatusName) {
        this.surveyStatusNo = surveyStatusNo;
        this.surveyStatusName = surveyStatusName;
    }

    public int getSurveyStatusNo() {
        return surveyStatusNo;
    }

    public String getSurveyStatusName() {
        return surveyStatusName;
    }

    public static SurveyStatusEnum convertTo(int surveyStatusNo) {
        for (SurveyStatusEnum status : values()) {
            if (status.getSurveyStatusNo() == surveyStatusNo) {
                return status;
            }
        }

        throw new NotFoundElementException("No Such Survey Status Type");
    }
}
