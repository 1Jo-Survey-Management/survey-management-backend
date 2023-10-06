package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 유저 API 컨트롤러 클래스입니다.
 *
 * <p>유저 관련 API 엔드포인트를 처리합니다.</p>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/survey/Mypage")
public class UserApi {
    private final UserServiceImpl userServiceImpl;

    /**
     * 유저 닉네임 수정 엔드포인트입니다.
     *
     * @param userNo      유저 번호
     * @param userModifyDTO 유저 수정 DTO
     * @return 응답 엔터티
     */
    @PutMapping("/{userNo}/nickname")
    public ResponseEntity<CommonResponse> userNickNameModify(
            @PathVariable("userNo") Long userNo, @RequestBody UserModifyDTO userModifyDTO) {
        boolean updated = userServiceImpl.updateUserNickName(userModifyDTO);

        if (updated) {
            return ResponseEntity
                    .ok()
                    .body(CommonResponse.successOf("Modify userNickName successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.error(ErrorResponse.of("Modify Fail")));
        }
    }

    /**
     * 유저 이미지 업데이트 엔드포인트
     *
     * @param userNo 유저 번호
     * @param file   이미지 파일
     * @return 업데이트 결과
     */
    @PutMapping("/{userNo}/image")
    public ResponseEntity<CommonResponse> updateUserNickname(
            @PathVariable long userNo,
            @RequestParam("file") MultipartFile file) {

        boolean updated = userServiceImpl.updateUserImage(userNo, file);

        if (updated) {
            return ResponseEntity
                    .ok()
                    .body(CommonResponse.<String>successOf("Image updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.<String>error(ErrorResponse.of("Image update failed")));
        }
    }

    /**
     * 유저 정보 조회 엔드포인트
     *
     * @param userNo 유저 번호
     * @return 응답 엔터티
     */
    @GetMapping("/{userNo}")
    public ResponseEntity<UserDTO> getUserByUserNo(@PathVariable("userNo") long userNo) {
        UserDTO userDTO = userServiceImpl.getUserByUserNo(userNo);

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(userDTO);
    }
}




