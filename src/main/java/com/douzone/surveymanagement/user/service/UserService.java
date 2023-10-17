package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.douzone.surveymanagement.user.dto.UserInfo;

/**
 * 유저 서비스 인터페이스입니다.
 */
public interface UserService {

    int beforeRegistUser(UserInfo userInfo);

    @Transactional
    int registUser(UserInfo userInfo);

    @Transactional
    int updateAccessToken(UserInfo userInfo);

    int deleteAccessToken(UserInfo userInfo);

    UserInfo findUserByUserAccessToken(String accessToken);

    UserInfo findUserByUserEmail(String userEmail);

    UserInfo findUserByAccessTokenAndUserNo(String accessToken, long userNo);

    /**
  
    UserInfo findUserByUserAccessToken(String accessToken);

    UserInfo findUserByUserEmail(String accessToken);

    UserInfo findUserByAccessTokenAndUserNo(String accessToken, long userNo);

    int updateAccessToken(UserInfo userInfo);

    /**
     * 유저의 닉네임을 변경합니다.
     *
     * @param userModifyDTO 유저 닉네임 수정 DTO
     * @return 변경된 레코드 수
     */
    boolean updateUserNickName(UserModifyDTO userModifyDTO);

    /**
     * 유저 번호로 유저 정보를 조회합니다.
     *
     * @param userNo 유저 번호
     * @return 유저 정보 DTO
     */
    UserDTO getUserByUserNo(long userNo);

    /**
     * 사용자 이름의 중복을 확인합니다.
     *
     * @param userModifyDTO 사용자 수정 DTO
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     */
    boolean duplicateUsername(UserModifyDTO userModifyDTO);

    /**
     * 사용자 닉네임의 중복 여부를 확인합니다.
     *
     * @param userNickname 확인할 사용자 닉네임
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     */
    boolean isUserNicknameDuplicate(String userNickname);

        /**
         * 유저의 이미지 정보를 업데이트합니다.
         *
         * @param userNo       유저 번호
         * @param File 새 이미지 파일
         * @return 업데이트 성공 여부 (true: 성공, false: 실패)
         */
    boolean updateUserImage(long userNo, MultipartFile File);

    /**
     * 이전 사용자 이미지를 삭제하고 데이터베이스를 업데이트합니다.
     *
     * @param userNo 삭제 및 업데이트를 수행할 사용자의 고유 번호
     */
    void deletePreviousUserImage(long userNo);

}
