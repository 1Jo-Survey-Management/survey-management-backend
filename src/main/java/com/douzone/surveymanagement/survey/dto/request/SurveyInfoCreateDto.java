package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.survey.annotation.FutureDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 설문의 정보를 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
@Schema(description = "새로운 설문의 기본 정보를 등록하기 위한 DTO")
public class SurveyInfoCreateDto {

    @JsonIgnore
    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;
    @Schema(description = "사용자 번호", example = "1")
    private long userNo;

    @Schema(description = "설문 상태 번호", example = "1")
    @NotNull(message = "설문 상태는 null일 수 없습니다.")
    private int surveyStatusNo;

    @Schema(description = "설문 공개 상태 번호", example = "1")
    @NotNull(message = "설문 공개 상태는 null일 수 없습니다.")
    private int openStatusNo;

    @Schema(description = "설문 제목", example = "설문 제목 예시")
    @NotNull(message = "설문의 제목은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "설문 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyTitle;

    @Schema(description = "설문 설명", example = "설문 설명 예시")
    @NotNull(message = "설문의 설명은 null일 수 없습니다.")
    @Min(value = 1, message = "설문 설명은 최소 1자보다 길어야 합니다.")
    private String surveyDescription;

    @Schema(description = "설문 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String surveyImageUrl;

    @Schema(description = "설문 게시 일시", example = "2023-01-01T00:00:00.000+00:00")
    private LocalDateTime surveyPostAt;

    @Schema(description = "설문 마감 일시", example = "2023-01-20T00:00:00.000+00:00")
    @FutureDateTime
    @NotNull(message = "설문의 마감일은 null일 수 없습니다.")
    private LocalDate surveyClosingAt;

    @Schema(description = "설문 태그 목록")
    @NotNull(message = "설문 태그는 null일 수 없습니다.")
    @Size(min = 1, max = 2, message = "태그는 1 ~ 2개만 설정이 가능합니다.")
    List<Integer> surveyTags;

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public void setSurveyPostAt(LocalDateTime surveyPostAt) {
        this.surveyPostAt = surveyPostAt;
    }
}
