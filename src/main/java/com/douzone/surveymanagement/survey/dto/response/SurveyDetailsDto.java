package com.douzone.surveymanagement.survey.dto.response;

import com.douzone.surveymanagement.surveyquestion.dto.response.QuestionDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "설문 상세 정보를 나타내는 DTO")
public class SurveyDetailsDto {

    @Schema(description = "설문 번호", example = "1")
    @JsonProperty("surveyId")
    private long surveyNo;
    @Schema(description = "설문 제목", example = "설문 제목 예시")
    private String surveyTitle;
    @Schema(description = "설문 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String surveyImage;
    @Schema(description = "설문 설명", example = "설문 설명 예시")
    private String surveyDescription;
    @Schema(description = "설문 마감 일시", example = "2023-12-31")
    private LocalDate surveyClosingAt;
    @Schema(description = "설문 공개 상태 번호", example = "1")
    private int openStatusNo;
    @Schema(description = "설문 상태 번호", example = "1")
    private int surveyStatusNo;
    @Schema(description = "설문 태그 목록", example = "교육, 기술")
    private String tagNames;
    @Schema(description = "설문의 문항 목록")
    @JsonProperty("questions")
    List<QuestionDetailsDto> questionDetailsDtoList;

}
