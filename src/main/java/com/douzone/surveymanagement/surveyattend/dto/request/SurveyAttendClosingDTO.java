package com.douzone.surveymanagement.surveyattend.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class SurveyAttendClosingDTO {
    private LocalDateTime surveyClosingAt;
    private long surveyNo;
}
