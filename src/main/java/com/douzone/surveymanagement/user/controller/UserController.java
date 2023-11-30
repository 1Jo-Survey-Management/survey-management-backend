package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.annotation.S3DeleteObject;
import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.exception.DuplicateUsernameException;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 API 컨트롤러 클래스입니다.
 *
 * <p>유저 관련 API 엔드포인트를 처리합니다.</p>
 *
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PutMapping("/nickname")
    @Operation(summary = "사용자 닉네임 업데이트", description = "로그인한 사용자의 닉네임을 업데이트합니다.")
    public ResponseEntity<CommonResponse> userNickNameUpdate(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody UserModifyDTO userModifyDTO) {

        try {
            userModifyDTO.setUserNo(userDetails.getUserNo());
            userServiceImpl.updateUserNickName(userModifyDTO);
            return ResponseEntity
                .ok()
                .body(CommonResponse.<String>successOf("NickName updated successfully"));
        } catch (DuplicateUsernameException e) {
            String errorMessage = "Duplicate username: " + e.getMessage();


            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.<String>error(ErrorResponse.of(errorMessage)));
        }
    }

    @S3DeleteObject
    @PutMapping("/image")
    @Operation(summary = "사용자 이미지 업데이트", description = "로그인한 사용자의 프로필 이미지를 업데이트합니다.")
    public ResponseEntity<CommonResponse> updateUserImage(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody ImageModifyDTO userImage) {

        boolean updated =
            userServiceImpl.updateUserImage(userDetails.getUserNo(), userImage.getUserImage());

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

    @GetMapping("/user-info")
    @Operation(summary = "현재 로그인한 사용자 정보 조회", description = "현재 로그인한 사용자의 상세 정보를 조회합니다.")
    public ResponseEntity<UserDTO> getCurrentUser(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO userDTO = userServiceImpl.getUserByUserNo(userDetails.getUserNo());

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(userDTO);
    }


    /**
     * 유저 정보 조회 엔드포인트
     *
     * @param userModifyDTO 유저 수정 DTO
     * @return 응답 엔터티
     */
    @PostMapping("/check-duplicate-nickname")
    @Operation(summary = "닉네임 중복 확인", description = "닉네임이 이미 사용 중인지 확인합니다.")
    public ResponseEntity<String> getUserByUserNickname(@RequestBody UserModifyDTO userModifyDTO) {
        boolean isDuplicate = userServiceImpl.duplicateUsername(userModifyDTO);

        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nickname is not available");
        }

        return ResponseEntity.ok("Nickname is available");
    }

    /**
     * 유효한 사용자인지 검사하는 메서드 입니다.
     *
     * @return 유효한 사용자일 경우 CommonResponse.success 반환
     * @author : 강명관
     */
    @Operation(
        summary = "사용자가 유효한 사용지 검사",
        description = "사용자가 유효한 상용자인지 확인합니다."
    )
    @GetMapping("/valid-check")
    public ResponseEntity<CommonResponse> userValidCheck() {
        return ResponseEntity.ok(CommonResponse.success());
    }


}




