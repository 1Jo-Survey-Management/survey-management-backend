package com.douzone.surveymanagement.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "설문 상세 정보를 나타내는 DTO")
public class SurveyDetailInfoDto {

    @Schema(description = "설문 번호", example = "1")
    private long surveyNo;
    @Schema(description = "설문 제목", example = "설문 제목 예시")
    private String surveyTitle;
    @Schema(description = "설문 설명", example = "설문 설명 예시")
    private String surveyDescription;
    @Schema(description = "설문 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String surveyImage;
    @Schema(description = "설문 게시 일시", example = "2023-01-01T00:00:00.000+00:00")
    private LocalDateTime surveyPostAt;
    @Schema(description = "설문 마감 일시", example = "2023-12-31")
    private LocalDate surveyClosingAt;
    @Schema(description = "사용자 번호", example = "1")
    private int userNo;
    @Schema(description = "사용자 닉네임", example = "nickname123")
    private String userNickName;
    @Schema(description = "사용자 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/userimage.jpg")
    private String userImage;
    @Schema(description = "설문 상태 이름", example = "진행중")
    private String surveyStatusName;
    @Schema(description = "설문 공개 상태 이름", example = "공개")
    private String openStatusName;
    @Schema(description = "설문 참여자 수", example = "100")
    private long surveyAttendCount;
    @Schema(description = "설문 삭제 여부", example = "false")
    private boolean isDeleted;
    @Schema(description = "설문 태그 목록", example = "[\"공지\", \"일상\"]")
    private List<String> tagName;

    @Schema(description = "참여한 사용자 목록")
    private List<Long> attendUserList;

    @Schema(description = "참여 여부 체크 목록")
    private List<Boolean> attendCheckList;
}
