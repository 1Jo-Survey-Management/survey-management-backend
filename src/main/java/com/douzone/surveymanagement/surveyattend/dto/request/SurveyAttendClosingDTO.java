package com.douzone.surveymanagement.surveyattend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Schema(description = "설문 참여 마감 관련 데이터 DTO")
public class SurveyAttendClosingDTO {
    @Schema(description = "설문 마감 일시", example = "2023-12-31T23:59:59")
    private LocalDateTime surveyClosingAt;
    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;
}
