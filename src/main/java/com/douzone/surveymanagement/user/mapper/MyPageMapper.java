package com.douzone.surveymanagement.user.mapper;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.ImageUploadDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    int modifyUserNickNameByUserNo(UserModifyDTO userModifyDTO);

    int insertUserImage(ImageUploadDTO imageUploadDTO);

    int updateUserImage(ImageModifyDTO imageModifyDTO);

    UserDTO getUserByUserNo(Long userNo);

}
