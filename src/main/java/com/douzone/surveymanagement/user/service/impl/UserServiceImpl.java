package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.exception.DuplicateUsernameException;
import com.douzone.surveymanagement.user.mapper.MyPageMapper;
import com.douzone.surveymanagement.user.mapper.UserMapper;
import com.douzone.surveymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저 서비스 구현체입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final MyPageMapper myPageMapper;
    private final UserMapper userMapper;

    /**
     * 회원가입 중의 가입 미완료 회원의 등록 메서드입니다
     *
     * @param userInfo
     * @return 성공여부
     * @author 김선규
     */
    @Override
    @Transactional
    public int beforeRegistUser(UserInfo userInfo) {
        int flag;

        flag = userMapper.beforeRegistUser(userInfo);

        return flag;
    }

    /**
     * 회원가입 유저 등록 메서드입니다
     *
     * @param userInfo
     * @author 김선규
     */
    @Override
    @Transactional
    public void registUser(UserInfo userInfo) {

        userMapper.registUser(userInfo);

    }

    /**
     * AccessToken 갱신 메서드입니다
     *
     * @param userInfo
     * @return 성공여부
     * @author 김선규
     */
    @Override
    @Transactional
    public int updateAccessToken(UserInfo userInfo) {
        int flag;

        flag = userMapper.updateAccessToken(userInfo);

        return flag;
    }

    @Override
    @Transactional
    public boolean loginCancel(String userNo) {
        boolean isCancelSuccessed = userMapper.loginCancel(userNo);
        return isCancelSuccessed;
    }

    /**
     * AccessToken으로 회원 조회하는 메서드입니다
     *
     * @param accessToken
     * @return 회원정보
     * @author 김선규
     */
    @Override
    public UserInfo findUserByUserAccessToken(String accessToken) {

        UserInfo userInfo = userMapper.findUserByUserAccessToken(accessToken);

        return userInfo;
    }

    /**
     * 유저 이메일로 회원 조회 하는 메서드입니다
     *
     * @param userEmail
     * @return 회원정보
     * @author 김선규
     */
    @Override
    public UserInfo findUserByUserEmail(String userEmail) {

        UserInfo userInfo = userMapper.selectAllByUserEmail(userEmail);

        return userInfo;
    }

    @Override
    @Transactional
    public boolean updateUserNickName(UserModifyDTO userModifyDTO) {
        UserModifyDTO existUserNickname =
            myPageMapper.getUserByUserNickname(userModifyDTO.getUserNickname());

        if (existUserNickname != null &&
            isUserNicknameDuplicate(existUserNickname.getUserNickname())) {
            throw new DuplicateUsernameException(
                "Duplicate username: " + userModifyDTO.getUserNickname());
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
        UserModifyDTO existUserNickname =
            myPageMapper.getUserByUserNickname(userModifyDTO.getUserNickname());
        return existUserNickname != null &&
            isUserNicknameDuplicate(existUserNickname.getUserNickname());
    }

    @Override
    public boolean isUserNicknameDuplicate(String userNickname) {
        UserModifyDTO existingUser = myPageMapper.getUserByUserNickname(userNickname);
        return existingUser != null;
    }

    @Override
    @Transactional
    public boolean updateUserImage(long userNo, String userImage) {
        deletePreviousUserImage(userNo);

        ImageModifyDTO imageModifyDTO = new ImageModifyDTO(userNo, userImage);

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

