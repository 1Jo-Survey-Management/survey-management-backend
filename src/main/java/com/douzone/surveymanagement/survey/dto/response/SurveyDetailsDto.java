package com.douzone.surveymanagement.survey.dto.response;

import com.douzone.surveymanagement.surveyquestion.dto.response.QuestionDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 설문에 대한 모든 정보를 받아오기 위한 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDetailsDto {

    @JsonProperty("surveyId")
    private long surveyNo;
    private String surveyTitle;
    private String surveyImage;
    private String surveyDescription;
    private LocalDate surveyClosingAt;
    private int openStatusNo;
    private int surveyStatusNo;
    private String tagNames;
    @JsonProperty("questions")
    List<QuestionDetailsDto> questionDetailsDtoList;

}
