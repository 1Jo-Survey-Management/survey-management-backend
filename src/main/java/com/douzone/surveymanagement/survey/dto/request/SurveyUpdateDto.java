package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 설문 수정에 필요한 데이터를 담아놓은 DTO 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@ToString
@Getter
@AllArgsConstructor
public class SurveyUpdateDto {

    @NotNull(message = "설문의 정보는 필수 입니다.")
    SurveyInfoUpdateDto surveyInfoUpdateDto;

    @NotNull(message = "설문의 문항은 필수 입니다.")
    @Size(min = 1, message = "설문의 문항의 최소 1개 이상입니다.")
    List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList;
}
