package com.douzone.surveymanagement.user.mapper;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 유저 번호를 통해 유저 이미지를 가져오는 쿼리 입니다.
     *
     * @param userNo 유저 번호
     * @return 유저 이미지 저장 경로
     * @author : 강명관
     */
    String selectUserImageByUserNo(@Param("userNo") long userNo);
}
