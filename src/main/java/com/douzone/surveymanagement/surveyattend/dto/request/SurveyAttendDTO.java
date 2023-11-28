package com.douzone.surveymanagement.surveyattend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 설문 참여 관련 데이터를 나타내는 'DTO' 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "설문 참여 관련 데이터 DTO")
public class SurveyAttendDTO {
    @Schema(description = "설문 제목", example = "설문 제목 예시")
    private String surveyTitle;
    @Schema(description = "설문 이미지 URL", example = "https://example.com/image.jpg")
    private String surveyImage;
    @Schema(description = "설문 문항 번호", example = "1")
    private long surveyQuestionNo;
    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;
    @Schema(description = "문항 유형 번호", example = "1")
    private int questionTypeNo;
    @Schema(description = "문항 제목", example = "문항 제목 예시")
    private String surveyQuestionTitle;
    @Schema(description = "문항 설명", example = "문항 설명 예시")
    private String surveyQuestionDescription;
    @Schema(description = "문항 필수 여부", example = "true")
    private boolean isRequired;
    @Schema(description = "선택지 번호", example = "1")
    private long selectionNo;
    @Schema(description = "설문 문항 이동 번호", example = "1")
    private long surveyQuestionMoveNo;
    @Schema(description = "선택지 값", example = "선택지 예시")
    private String selectionValue;
    @Schema(description = "선택지 이동 가능 여부", example = "true")
    private boolean isMovable;
    @Schema(description = "설문 종료 여부", example = "false")
    private boolean isEndOfSurvey;
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;
    @Schema(description = "설문 참여 번호", example = "1")
    private long surveyAttendNo;
    @Schema(description = "주관식 답변", example = "주관식 답변 예시")
    private String surveySubjectiveAnswer;
    @Schema(description = "설문 답변 번호", example = "1")
    private long surveyAnswerNo;

}
