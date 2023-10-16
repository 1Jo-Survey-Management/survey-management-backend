package com.douzone.surveymanagement.user.service;

import com.douzone.surveymanagement.user.dto.UserInfo;

/**
 * 로그인을 위한 UserService 입니다
 * @author 김선규
 */
public interface UserService {

    UserInfo findUserByUserAccessToken(String accessToken);

    UserInfo findUserByUserEmail(String accessToken);

    UserInfo findUserByAccessTokenAndUserNo(String accessToken, long userNo);

    int updateAccessToken(UserInfo userInfo);
}
