package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
public class SurveyCreateDto {

    @NotNull
    @Valid
    private SurveyInfoCreateDto surveyInfoCreateDto;

    @NotNull
    @NotEmpty
    @Valid
    private List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList;

    @Valid
    private List<SelectionCreateDto> selectionCreateDtoList;


}
