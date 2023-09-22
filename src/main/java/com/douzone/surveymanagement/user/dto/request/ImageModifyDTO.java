package com.douzone.surveymanagement.user.dto.request;

import lombok.*;

/**
 * 이미지 정보 수정 DTO 클래스입니다.
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ImageModifyDTO {
    private long userNo;
    private String userImage;
}
