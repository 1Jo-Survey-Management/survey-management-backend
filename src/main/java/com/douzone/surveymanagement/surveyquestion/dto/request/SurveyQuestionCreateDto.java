package com.douzone.surveymanagement.surveyquestion.dto.request;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 설문 문항을 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
@Schema(description = "설문 문항 등록을 위한 DTO")
public class SurveyQuestionCreateDto {

    @JsonIgnore
    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;

    @JsonIgnore
    @Schema(description = "설문 문항 번호", example = "1")
    private long surveyQuestionNo;

    @Schema(description = "문항 유형 번호", example = "1")
    @JsonProperty("questionType")
    @NotNull(message = "문항 번호는 null일 수 없습니다.")
    private int questionTypeNo;

    @Schema(description = "문항 제목", example = "문항 제목 예시")
    @JsonProperty("questionTitle")
    @NotNull(message = "문항 제목은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "문항 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyQuestionTitle;

    @Schema(description = "문항 설명", example = "문항 설명 예시")
    @JsonProperty("questionDescription")
    private String surveyQuestionDescription;

    @Schema(description = "문항 필수 여부", example = "true")
    @JsonProperty("questionRequired")
    @NotNull(message = "필수 여부는 null일 수 없습니다.")
    boolean isRequired;

    @Schema(description = "선택지 목록")
    @Valid
    @JsonProperty("selections")
    private List<SelectionCreateDto> selectionCreateDtoList;

    public void setSurveyNo(long surveyNo) {
        this.surveyNo = surveyNo;
    }
}
