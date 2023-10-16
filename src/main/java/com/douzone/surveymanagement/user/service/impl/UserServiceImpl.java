package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.mapper.UserMapper;
import com.douzone.surveymanagement.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 사용자 로그인 관련 서비스 클래스입니다.
 * @author 김선규
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public int beforeRegistUser(UserInfo userInfo){
        int flag ;

        flag = userMapper.beforeRegistUser(userInfo);

        return flag;
    }

    public int registUser(UserInfo userInfo){
        int flag ;

        flag = userMapper.registUser(userInfo);

        return flag;
    }

    public int updateAccessToken(UserInfo userInfo){
        int flag ;

        flag = userMapper.updateAccessToken(userInfo);

        return flag;
    }

    public int deleteAccessToken(UserInfo userInfo){
        int flag ;

        flag = userMapper.deleteAccessToken(userInfo);

        return flag;
    }

    @Override
    public UserInfo findUserByUserAccessToken(String accessToken){

        UserInfo userInfo = userMapper.findUserByUserAccessToken(accessToken);

        return userInfo;
    }

    @Override
    public UserInfo findUserByUserEmail(String userEmail){

        UserInfo userInfo = userMapper.selectAllByUserEmail(userEmail);

        return userInfo;
    }

    @Override
    public UserInfo findUserByAccessTokenAndUserNo(String accessToken, long userNo){

        UserInfo userInfo = userMapper.findUserByAccessTokenAndUserNo(accessToken, userNo);

        return userInfo;
    }



}
