package com.douzone.surveymanagement.user.service;

import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 유저 서비스 인터페이스입니다.
 */
public interface UserService {

    /**
     * 유저의 닉네임을 변경합니다.
     *
     * @param userModifyDTO 유저 닉네임 수정 DTO
     * @return 변경된 레코드 수
     */
    boolean updateUserNickName(UserModifyDTO userModifyDTO);

    /**
     * 유저 정보를 조회합니다.
     *
     * @param userNo 유저 번호
     * @return 유저 정보 DTO
     */
    UserDTO getUserByUserNo(long userNo);

    /**
     * 유저의 이미지 정보를 업데이트합니다.
     *
     * @param userNo       유저 번호
     * @param file 새 이미지 파일
     * @return 업데이트 성공 여부 (true: 성공, false: 실패)
     */
    boolean updateUserImage(long userNo, MultipartFile file);

    /**
     * 이전 사용자 이미지를 삭제하고 데이터베이스를 업데이트합니다.
     *
     * @param userNo 삭제 및 업데이트를 수행할 사용자의 고유 번호
     */
    void deletePreviousUserImage(long userNo);
}
