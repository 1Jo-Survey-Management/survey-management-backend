package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.mapper.UserMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 사용자 로그인 관련 서비스 클래스입니다.
 * @author 김선규
 */
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


//  private final UserServiceImpl2 userServiceImpl2;
    private final UserMapper userMapper;

    @Transactional
    public int loginCheck(String userEmail){
        int flag = 0;

        flag = userMapper.findUserByUserEmail(userEmail);

        return flag;
    }




}
