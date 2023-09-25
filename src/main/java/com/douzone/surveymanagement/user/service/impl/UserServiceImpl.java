package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.mapper.UserMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 사용자 로그인 관련 서비스 클래스입니다.
 * @author 김선규
 */
@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    //  private final UserServiceImpl2 userServiceImpl2;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    public UserInfo selectAllByUserEmail(String userEmail){
        UserInfo user;

        user = userMapper.selectAllByUserEmail(userEmail);

        return user;
    }


    public int loginCheck(String userEmail){
        int flag = 0;

        flag = userMapper.findUserByUserEmail(userEmail);

        return flag;
    }

    @Override
    public UserInfo findUserByUserAccessToken(String accessToken){

        System.out.println("userimpl : " + accessToken);

        UserInfo userInfo = userMapper.findUserByUserAccessToken(accessToken);

        System.out.println("userimpldd : " + userInfo);

        return userInfo;
    }

}
