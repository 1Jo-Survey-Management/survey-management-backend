package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.survey.annotation.FutureDateTime;
import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * 설문의 정보를 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
public class SurveyInfoCreateDto {

    @JsonIgnore
    private long surveyNo;
    @NotNull(message = "유저 번호는 null일 수 없습니다.")
    private long userNo;

    @NotNull(message = "설문 상태는 null일 수 없습니다.")
    private int surveyStatusNo;

    @NotNull(message = "설문 공개 상태는 null일 수 없습니다.")
    private int openStatusNo;

    @NotNull(message = "설문의 제목은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "설문 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyTitle;

    @NotNull(message = "설문의 설명은 null일 수 없습니다.")
    @Min(value = 1, message = "설문 설명은 최소 1자보다 길어야 합니다.")
    private String surveyDescription;

    private String surveyImagePath;

    public void setSurveyImagePath(String surveyImagePath) {
        this.surveyImagePath = surveyImagePath;
    }
    private LocalDateTime surveyPostAt;

    @FutureDateTime
    @NotNull(message = "설문의 마감일은 null일 수 없습니다.")
    private LocalDate surveyClosingAt;

    @NotNull(message = "설문 태그는 null일 수 없습니다.")
    @Size(min = 1, max = 2, message = "태그는 1 ~ 2개만 설정이 가능합니다.")
    List<Integer> surveyTags;
}