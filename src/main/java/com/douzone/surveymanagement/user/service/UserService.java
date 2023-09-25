package com.douzone.surveymanagement.user.service;

import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {


    UserInfo findUserByUserAccessToken(String accessToken);
}
