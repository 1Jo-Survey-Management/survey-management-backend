package com.douzone.surveymanagement.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Schema(description = "설문 통계 관련 DTO")
public class SelectDto {
    @Schema(description = "설문 번호", example = "1")
    private final long surveyNo;
    @Schema(description = "설문 제목", example = "설문 제목 예시")
    private final String surveyTitle;
    @Schema(description = "설문 작성자", example = "작성자 이름")
    private final String surveyWriter;
    @Schema(description = "사용자 닉네임", example = "nickname123")
    private final String userNickname;
    @Schema(description = "설문 게시 일시", example = "2023-01-01T00:00:00.000+00:00")
    private final String surveyPostAt;
    @Schema(description = "설문 마감 일시", example = "2023-01-20T00:00:00.000+00:00")
    private final String surveyClosingAt;
    @Schema(description = "설문 문항 제목", example = "문항 제목 예시")
    private final String surveyQuestionTitle;
    @Schema(description = "설문 문항 번호", example = "1")
    private final String surveyQuestionNo;
    @Schema(description = "문항 유형 번호", example = "1")
    private final int questionTypeNo;
    @Schema(description = "선택지 번호", example = "1")
    private final long selectionNo;
    @Schema(description = "선택지 값", example = "선택지 예시")
    private final String selectionValue;
    @Schema(description = "선택지 카운트", example = "10")
    private final int selectionCount;
    @Schema(description = "총 참여자 수", example = "100")
    private final int totalAttend;
    @Schema(description = "문항별 참여자 수", example = "50")
    private final int questionAttendCount;
    @Schema(description = "주관식 답변", example = "주관식 답변 예시")
    private final String surveySubjectiveAnswer;
    @Schema(description = "주관식 답변 카운트", example = "5")
    private final int surveySubjectiveAnswerCount;
    @Schema(description = "설문 공개 상태 번호", example = "1")
    private final int openStatusNo;

}
