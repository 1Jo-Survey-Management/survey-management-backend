package com.douzone.surveymanagement.survey.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyDetailInfoDto {

    private  long surveyNo;
    private  String surveyTitle;
    private  String surveyDiscription;
    private  String surveyImage;
    private  LocalDateTime surveyPostAt;
    private  LocalDateTime surveyClosingAt;
    private  String userNickName;
    private  String userImage;
    private  String surveyStatusName;
    private  String openStatusName;

    private  long surveyAttendCount;
    private  boolean isDeleted;
    private List<String> tag;

}
