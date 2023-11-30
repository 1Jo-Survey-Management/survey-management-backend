package com.douzone.surveymanagement.mysurvey.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 설문 정보 DTO 클래스입니다.
 *
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Schema(description = "사용자 설문 정보 DTO")
public class MySurveyDTO {
    @Schema(description = "설문 번호", example = "1")
    private Long surveyNo;
    @Schema(description = "사용자 번호", example = "1")
    private Long userNo;
    @Schema(description = "사용자 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/image.jpg")
    private String userImage;
    @Schema(description = "사용자 닉네임", example = "nickname123")
    private String userNickname;
    @Schema(description = "설문 상태 번호", example = "1")
    private Long surveyStatusNo;
    @Schema(description = "설문 제목", example = "설문 제목 예시")
    private String surveyTitle;
    @Schema(description = "설문 설명", example = "설문 설명 예시")
    private String surveyDescription;
    @Schema(description = "설문 대표 이미지 URL", example = "https://survey-management-bucket.s3.ap-northeast-2.amazonaws.com/survey-management-images/surveyimage.jpg")
    private String surveyImage;
    @Schema(description = "설문 생성 일시", example = "2023-01-01T00:00:00.000+00:00")
    private String surveyCreatedAt;
    @Schema(description = "설문 게시 일시", example = "2023-01-10T00:00:00.000+00:00")
    private String surveyPostAt;
    @Schema(description = "설문 마감 일시", example = "2023-01-20T00:00:00.000+00:00")
    private String surveyClosingAt;
    @Schema(description = "설문 삭제 여부", example = "0")
    private Integer isDeleted;
    @Schema(description = "설문 공개 상태 번호", example = "1")
    private Integer openStatusNo;
    @Schema(description = "설문 태그 이름 목록")
    private List<String> tagNames;
    @Schema(description = "참여자 수", example = "50")
    private Integer attendeeCount;
    @Schema(description = "설문 참여 번호", example = "1")
    private Long surveyAttendNo;
    @Schema(description = "설문 참여 생성 일시", example = "2023-01-05T00:00:00.000+00:00")
    private String surveyAttendCreatedAt;
}
