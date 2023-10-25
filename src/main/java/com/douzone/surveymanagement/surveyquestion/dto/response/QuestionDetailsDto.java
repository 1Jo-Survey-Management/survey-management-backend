package com.douzone.surveymanagement.surveyquestion.dto.response;

import com.douzone.surveymanagement.selection.dto.response.SelectionDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 문항에 대한 모든 정보를 담은 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailsDto {

    @JsonProperty("surveyId")
    private long surveyNo;

    @JsonProperty("questionId")
    private long surveyQuestionNo;
    private int questionTypeNo;
    private String surveyQuestionTitle;
    private String surveyQuestionDescription;
    private boolean isRequired;
    @JsonProperty("selections")
    List<SelectionDetailsDto> selectionDetailsDtoList;
}
