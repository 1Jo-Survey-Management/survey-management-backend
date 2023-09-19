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

    @NotNull(message = "설문에 대한 정보는 null일 수 없습니다.")
    @Valid
    private SurveyInfoCreateDto surveyInfoCreateDto;

    @NotNull(message = "설문에 대한 문항은 null일 수 없습니다.")
    @NotEmpty(message = "설문에 대해 최소 한개 이상의 문항이 있어야 합니다.")
    @Valid
    private List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList;

    @Valid
    private List<SelectionCreateDto> selectionCreateDtoList;


}
