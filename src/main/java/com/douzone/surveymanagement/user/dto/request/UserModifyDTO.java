package com.douzone.surveymanagement.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserModifyDTO {
    private Long userNo;

    private String userNickName;

    private String userImage;
}
