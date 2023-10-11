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


    public int beforeRegistUser(UserInfo userInfo){
        int flag = 0;

        flag = userMapper.beforeRegistUser(userInfo);

        return flag;
    }

    public int registUser(UserInfo userInfo){
        int flag = 0;

        flag = userMapper.registUser(userInfo);

        return flag;
    }

    public int updateAccessToken(UserInfo userInfo){
        int flag = 0;

        flag = userMapper.updateAccessToken(userInfo);

        return flag;
    }

    @Override
    public UserInfo findUserByUserAccessToken(String accessToken){

        System.out.println("userimpl : " + accessToken);

        UserInfo userInfo = userMapper.findUserByUserAccessToken(accessToken);

        System.out.println("userimpldd : " + userInfo);

        return userInfo;
    }

    @Override
    public UserInfo findUserByUserEmail(String userEmail){

        System.out.println("userimpl : " + userEmail);

        UserInfo userInfo = userMapper.selectAllByUserEmail(userEmail);

        System.out.println("userimpldd : " + userInfo);

        return userInfo;
    }

    @Override
    public UserInfo findUserByAccessTokenAndUserNo(String accessToken, long userNo){

        System.out.println("userimplaccessToken : " + accessToken);
        System.out.println("userimpluserNo : " + userNo);

        UserInfo userInfo = userMapper.findUserByAccessTokenAndUserNo(accessToken, userNo);

        System.out.println("userimpldd : " + userInfo);

        return userInfo;
    }



}
