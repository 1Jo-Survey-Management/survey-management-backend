package com.douzone.surveymanagement.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String id;
    private String nickname;
    private String email;
    private String name;
    private String userRole;

}