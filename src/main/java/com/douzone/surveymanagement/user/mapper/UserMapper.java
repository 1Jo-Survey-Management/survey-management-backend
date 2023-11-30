package com.douzone.surveymanagement.user.mapper;

import com.douzone.surveymanagement.user.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 로그인에 필요한 userMapper 입니다
 *
 * @author 김선규
 */
@Mapper
public interface UserMapper {
    UserInfo selectAllByUserEmail(String userEmail);

    UserInfo findUserByUserAccessToken(@Param("accessToken") String accessToken);

    int beforeRegistUser(UserInfo userInfo);

    int registUser(UserInfo userInfo);

    int updateAccessToken(UserInfo userInfo);

    boolean loginCancel(String userNo);
}
