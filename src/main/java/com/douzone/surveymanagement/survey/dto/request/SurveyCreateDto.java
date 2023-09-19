package com.douzone.surveymanagement.survey.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
@Builder
public class SurveyCreateDto {
    private long userNo;
    private int surveyStatusNo;
    private int openStatusNo;
    private String surveyTitle;
    private String surveyDescription;
    private String surveyImage;
    private LocalDateTime surveyPostAt;
    private LocalDateTime surveyClosingAt;
}
