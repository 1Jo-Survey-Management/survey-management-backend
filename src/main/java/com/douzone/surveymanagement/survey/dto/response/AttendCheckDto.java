package com.douzone.surveymanagement.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "사용자의 설문 참여 여부를 나타내는 DTO")
public class AttendCheckDto {

    @Schema(description = "참여 여부", example = "true")
    private boolean attendCheck;
}
