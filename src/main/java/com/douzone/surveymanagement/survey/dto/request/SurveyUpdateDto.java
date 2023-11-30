package com.douzone.surveymanagement.survey.dto.request;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "설문 수정을 위한 DTO")
public class SurveyUpdateDto {

    @Schema(description = "수정할 설문의 정보를 담고 있는 DTO")
    @NotNull(message = "설문의 정보는 필수 입니다.")
    SurveyInfoUpdateDto surveyInfoUpdateDto;

    @Schema(description = "수정할 설문의 문항 목록")
    @NotNull(message = "설문의 문항은 필수 입니다.")
    @Size(min = 1, message = "설문의 문항의 최소 1개 이상입니다.")
    List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList;
}
