package com.douzone.surveymanagement.user.mapper;

import com.douzone.surveymanagement.user.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    int findUserByUserEmail(String userEmail);
    int findUserByUserId(Long userId);

    UserInfo selectAllByUserEmail(String userEmail);

    UserInfo findUserByUserAccessToken(@Param("accessToken") String accessToken);
}
