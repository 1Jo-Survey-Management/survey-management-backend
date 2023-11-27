package com.douzone.surveymanagement.survey.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyDetailInfoDto {

    private  long surveyNo;
    private  String surveyTitle;
    private  String surveyDescription;
    private  String surveyImage;
    private  LocalDateTime surveyPostAt;
    private LocalDate surveyClosingAt;
    private  int userNo;
    private  String userNickName;
    private  String userImage;
    private  String surveyStatusName;
    private  String openStatusName;

    private  long surveyAttendCount;
    private  boolean isDeleted;
    private List<String> tagName;

    private List<Long> attendUserList;

    private List<Boolean> attendCheckList;
}
