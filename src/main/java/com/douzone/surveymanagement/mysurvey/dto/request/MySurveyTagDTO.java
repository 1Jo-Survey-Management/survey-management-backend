package com.douzone.surveymanagement.mysurvey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그 정보 DTO 클래스입니다.
 *
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MySurveyTagDTO {
    private int tagNo;
    private String tagNames;
}
