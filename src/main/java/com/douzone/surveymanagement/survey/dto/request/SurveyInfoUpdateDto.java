package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.survey.annotation.FutureDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class SurveyInfoUpdateDto {

    @JsonProperty(value = "surveyId")
    @NotNull(message = "설문 번호는 필수입니다.")
    private long surveyNo;

    @NotNull(message = "설문 제목은 필수입니다.")
    @Length(min = 1, max = 255, message = "설문 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyTitle;

    @NotNull(message = "설문 설명은 필수 입니다.")
    @Min(value = 1, message = "설문 설명은 최소 1자보다 길어야 합니다.")
    private String surveyDescription;
    private String surveyImage;

    @FutureDateTime
    @NotNull(message = "설문의 마감일은 필수 입니다.")
    private LocalDate surveyClosingAt;

    @NotNull(message = "설문의 공개 상태 정보는 필수입니다.")
    private int openStatusNo;

    public void setSurveyImage(String surveyImage) {
        this.surveyImage = surveyImage;
    }
}
