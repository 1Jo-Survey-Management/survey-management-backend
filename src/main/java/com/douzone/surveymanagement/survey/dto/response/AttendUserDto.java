package com.douzone.surveymanagement.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "설문에 참여한 사용자의 번호 DTO")
public class AttendUserDto {
    @Schema(description = "참여한 사용자의 번호", example = "1")
    private long attendUserNo;
}
