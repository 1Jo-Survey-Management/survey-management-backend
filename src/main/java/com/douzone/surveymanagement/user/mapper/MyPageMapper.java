package com.douzone.surveymanagement.user.mapper;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 마이페이지 매퍼 인터페이스입니다.
 */
@Mapper
public interface MyPageMapper {
    int updateUserNickNameByUserNo(UserModifyDTO userModifyDTO);

    int updateUserImage(ImageModifyDTO imageModifyDTO);

    UserDTO getUserByUserNo(long userNo);

    UserModifyDTO getUserByUserNickname(String userNickname);

    void deletePreviousUserImage(long userNo);
}
