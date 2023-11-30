package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.survey.annotation.FutureDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 설문의 정보를 수정하기 위한 정보를 담은 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
@Schema(description = "설문 정보 수정을 위한 DTO")
public class SurveyInfoUpdateDto {

    @Schema(description = "설문 번호", example = "1")
    @JsonProperty(value = "surveyId")
    @NotNull(message = "설문 번호는 필수입니다.")
    private long surveyNo;

    @Schema(description = "설문 제목", example = "설문 제목 예시")
    @NotNull(message = "설문 제목은 필수입니다.")
    @Length(min = 1, max = 255, message = "설문 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyTitle;

    @Schema(description = "설문 설명", example = "설문 설명 예시")
    @NotNull(message = "설문 설명은 필수 입니다.")
    @Min(value = 1, message = "설문 설명은 최소 1자보다 길어야 합니다.")
    private String surveyDescription;

    @Schema(description = "설문 태그 목록")
    @NotNull(message = "설문 태그는 필수 입니다.")
    @Size(min = 1, max = 2, message = "설문의 태그는 1 ~ 2개 입니다.")
    private List<Integer> surveyTags;

    @Schema(description = "설문 대표 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    @JsonProperty(value = "surveyImageUrl")
    @NotNull(message = "설문 대표 이미지는 필수 입니다.")
    private String surveyImage;

    @Schema(description = "설문 마감일", example = "2023-12-31")
    @FutureDateTime
    @NotNull(message = "설문의 마감일은 필수 입니다.")
    private LocalDate surveyClosingAt;

    @Schema(description = "설문 공개 상태 번호", example = "1")
    @NotNull(message = "설문의 공개 상태 정보는 필수입니다.")
    private int openStatusNo;

    public void setSurveyImage(String surveyImage) {
        this.surveyImage = surveyImage;
    }
}
