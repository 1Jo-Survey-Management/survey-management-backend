package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
@Schema(description = "새로운 설문 생성을 위한 DTO")
public class SurveyCreateDto {

    @Schema(description = "설문 기본 정보")
    @NotNull(message = "설문에 대한 정보는 null일 수 없습니다.")
    @Valid
    private SurveyInfoCreateDto surveyInfoCreateDto;

    @Schema(description = "설문에 포함될 문항 목록")
    @NotNull(message = "설문에 대한 문항은 null일 수 없습니다.")
    @NotEmpty(message = "설문에 대해 최소 한개 이상의 문항이 있어야 합니다.")
    @Valid
    private List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList;

}
