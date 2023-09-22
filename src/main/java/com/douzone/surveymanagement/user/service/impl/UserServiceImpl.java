package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.common.utils.FileUploadUtil;
import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.mapper.MyPageMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 유저 서비스 구현체입니다.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MyPageMapper myPageMapper;


    @Override
    public boolean updateUserNickName(UserModifyDTO userModifyDTO) {
        int updatedRows = myPageMapper.updateUserNickNameByUserNo(userModifyDTO);
        return updatedRows > 0;
    }

    @Override
    public UserDTO getUserByUserNo(long userNo) {
        return myPageMapper.getUserByUserNo(userNo);
    }

    @Override
    @Transactional
    public boolean updateUserImage(long userNo, MultipartFile file) {
        try {
            deletePreviousUserImage(userNo);

            String imagePath = FileUploadUtil.uploadFile(file);

            ImageModifyDTO imageModifyDTO = new ImageModifyDTO(userNo, imagePath);

            int updatedRows = myPageMapper.updateUserImage(imageModifyDTO);

            return updatedRows > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public void deletePreviousUserImage(long userNo) {
        UserDTO userDTO = myPageMapper.getUserByUserNo(userNo);
        String previousImagePath = userDTO.getUserImage();

        if (previousImagePath != null && !previousImagePath.isEmpty()) {
            try {
                Path previousImageFilePath = Paths.get(previousImagePath);
                Files.delete(previousImageFilePath.toAbsolutePath().normalize());

                myPageMapper.deletePreviousUserImage(userNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


