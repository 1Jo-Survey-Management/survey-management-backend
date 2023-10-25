package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.common.utils.FileUploadUtil;
import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.exception.DuplicateUsernameException;
import com.douzone.surveymanagement.user.mapper.MyPageMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final MyPageMapper myPageMapper;


    @Override
    @Transactional
    public boolean updateUserNickName(UserModifyDTO userModifyDTO) {
        UserModifyDTO existUserNickname = myPageMapper.getUserByUserNickname(userModifyDTO.getUserNickname());

        if (existUserNickname != null && isUserNicknameDuplicate(existUserNickname.getUserNickname())) {
            throw new DuplicateUsernameException("Duplicate username: " + userModifyDTO.getUserNickname());
        }

        int updatedRows = myPageMapper.updateUserNickNameByUserNo(userModifyDTO);
        return updatedRows > 0;
    }

    @Override
    public UserDTO getUserByUserNo(long userNo) {
        return myPageMapper.getUserByUserNo(userNo);
    }


    @Override
    public boolean duplicateUsername(UserModifyDTO userModifyDTO) {
        UserModifyDTO existUserNickname = myPageMapper.getUserByUserNickname(userModifyDTO.getUserNickname());
        return existUserNickname != null && isUserNicknameDuplicate(existUserNickname.getUserNickname());
    }

    @Override
    public boolean isUserNicknameDuplicate(String userNickname) {
        UserModifyDTO existingUser = myPageMapper.getUserByUserNickname(userNickname);
        return existingUser != null;
    }

    @Override
    @Transactional
    public boolean updateUserImage(long userNo, MultipartFile File) {
        deletePreviousUserImage(userNo);

        String imagePath = FileUploadUtil.uploadFile(File);

        ImageModifyDTO imageModifyDTO = new ImageModifyDTO(userNo, imagePath);

        int updatedRows = myPageMapper.updateUserImage(imageModifyDTO);

        return updatedRows > 0;
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

    @Override
    public String findUserImageByUserNo(long userNo) {
        return myPageMapper.selectUserImageByUserNo(userNo);
    }
}


