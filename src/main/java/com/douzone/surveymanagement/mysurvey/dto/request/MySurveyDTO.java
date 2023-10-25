package com.douzone.surveymanagement.mysurvey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;

/**
 * 설문 정보 DTO 클래스입니다.
 *
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MySurveyDTO {
    private Long surveyNo;
    private Long userNo;
    private String userNickname;
    private Long surveyStatusNo;
    private String surveyTitle;
    private String surveyDescription;
    private String surveyImage;
    private String surveyCreatedAt;
    private String surveyPostAt;
    private String surveyClosingAt;
    private Integer isDeleted;
    private Integer openStatusNo;
    private List<String> tagNames;
    private Integer attendeeCount;
    private Long surveyAttendNo;
    private String surveyAttendCreatedAt;
}
