package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.ImageUploadDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.mapper.MyPageMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MyPageMapper myPageMapper;


    @Override
    public int modifyUserNickName(UserModifyDTO userModifyDTO) {
        int updatedRows = myPageMapper.modifyUserNickNameByUserNo(userModifyDTO);

        if (updatedRows > 0) {
            System.out.println("Modified userNickName!!");
        } else {
            System.out.println("Failed to modify userNickName.");
        }

        return updatedRows;
    }

    @Override
    public int uploadUserImage(ImageUploadDTO imageUploadDTO) {
        return myPageMapper.insertUserImage(imageUploadDTO);
    }

    @Override
    public int modifyUserImage(ImageModifyDTO imageModifyDTO) {
        return myPageMapper.updateUserImage(imageModifyDTO);
    }

    @Override
    public UserDTO getUserByUserNo(Long userNo) {
        return myPageMapper.getUserByUserNo(userNo);
    }

    @Override
    public int updateUserImage(Long userNo, String imageNewPath) {
        // 이미지 정보를 업데이트하는 DTO 생성
        ImageModifyDTO imageModifyDTO = new ImageModifyDTO();
        imageModifyDTO.setUserNo(userNo);
        imageModifyDTO.setUserImage(imageNewPath);

        // 이미지 정보 업데이트
        return myPageMapper.updateUserImage(imageModifyDTO);
    }
}


