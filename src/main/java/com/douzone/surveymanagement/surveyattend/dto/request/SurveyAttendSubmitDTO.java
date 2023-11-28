package com.douzone.surveymanagement.surveyattend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 설문 참여 제출 관련 데이터를 나타내는 'DTO' 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 */
@ToString
@Getter
@Setter
@Schema(description = "설문 참여 제출을 위한 DTO")
public class SurveyAttendSubmitDTO {
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;
    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;
    @Schema(description = "설문 문항 번호", example = "1")
    private long surveyQuestionNo;
    @Schema(description = "문항 유형 번호", example = "1")
    private int questionTypeNo;
    @Schema(description = "선택지 번호", example = "1")
    private long selectionNo;
    @Schema(description = "설문 문항 제목", example = "설문 문항 제목 예시")
    private String surveyQuestionTitle;
    @Schema(description = "선택지 값", example = "선택지 예시")
    private String selectionValue;
    @Schema(description = "주관식 답변 내용", example = "주관식 답변 예시")
    private String surveySubjectiveAnswer;
    @Schema(description = "설문 참여 번호", example = "1")
    private long surveyAttendNo;
    @Schema(description = "설문 답변 번호", example = "1")
    private long surveyAnswerNo;
    @Schema(description = "설문 답변 선택지 번호", example = "1")
    private long surveyAnswerSelectionNo;
}
