package com.douzone.surveymanagement.user.service;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.ImageUploadDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;

public interface UserService {

    /**
     * 유저의 닉네임을 변경합니다.
     *
     * @param userModifyDTO
     * @return 변경된 레코드 수
     */
    int modifyUserNickName(UserModifyDTO userModifyDTO);

    int uploadUserImage(ImageUploadDTO imageUploadDTO);

    int modifyUserImage(ImageModifyDTO imageModifyDTO);

    UserDTO getUserByUserNo(Long userNo);



    /**
     * 유저의 이미지 정보를 업데이트합니다.
     *
     * @param userNo       유저 번호
     * @param imageNewPath 새 이미지 파일 경로
     * @return 업데이트 결과 (변경된 레코드 수)
     */
    int updateUserImage(Long userNo, String imageNewPath);

}
