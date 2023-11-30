package com.douzone.surveymanagement.surveyquestion.dto.response;

import com.douzone.surveymanagement.selection.dto.response.SelectionDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "설문 문항에 대한 상세 정보를 담는 DTO")
public class QuestionDetailsDto {

    @Schema(description = "설문 번호", example = "1")
    @JsonProperty("surveyId")
    private long surveyNo;

    @Schema(description = "설문 문항 번호", example = "1")
    @JsonProperty("questionId")
    private long surveyQuestionNo;
    @Schema(description = "문항 유형 번호", example = "1")
    private int questionTypeNo;
    @Schema(description = "문항 제목", example = "문항 제목 예시")
    private String surveyQuestionTitle;
    @Schema(description = "문항 설명", example = "문항 설명 예시")
    private String surveyQuestionDescription;
    @Schema(description = "문항 필수 여부", example = "true")
    private boolean isRequired;
    @Schema(description = "문항 선택지 목록")
    @JsonProperty("selections")
    List<SelectionDetailsDto> selectionDetailsDtoList;
}
