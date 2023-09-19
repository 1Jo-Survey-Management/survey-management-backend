package com.douzone.surveymanagement.surveyquestion.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 설문 문항을 등록하기 위한 Dto 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@AllArgsConstructor
@Builder
public class SurveyQuestionCreateDto {

    @NotNull(message = "설문 번호는 null일 수 없습니다.")
    private int surveyNo;

    @NotNull(message = "문항 번호는 null일 수 없습니다.")
    private int questionTypeNo;

    @NotNull(message = "문항 제목은 null일 수 없습니다.")
    @Length(min = 1, max = 255, message = "문항 제목은 최소 1자보다 크고 255자 보다 작아야 합니다.")
    private String surveyQuestionTitle;

    private String surveyQuestionDescription;

    @NotNull(message = "필수 여부는 null일 수 없습니다.")
    boolean isRequired;
}
