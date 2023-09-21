package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.user.dto.request.UserDTO;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.service.impl.FileUploadServiceImpl;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/survey/Mypage")
public class UserApi {
    private final UserServiceImpl userServiceImpl;
    private final FileUploadServiceImpl fileUploadService;


    @PatchMapping("/modify-nickname")
    public ResponseEntity<CommonResponse> userNickNameModify(@RequestBody UserModifyDTO userModifyDTO) {
        int updatedRows = userServiceImpl.modifyUserNickName(userModifyDTO);

        if (updatedRows == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.error(ErrorResponse.of("Modify Fail")));
        }

        return ResponseEntity
                .ok()
                .body(CommonResponse.successOf("Modify userNickName successfully"));
    }

    @PatchMapping("/update-image")
    public ResponseEntity<CommonResponse> updateUserImage(
            @RequestParam("userNo") Long userNo,
            @RequestParam("file") MultipartFile file) {
        try {
            // 이미지 업로드 및 정보 업데이트
            String imagePath = fileUploadService.uploadImageAndUpdateUser(userNo, file);

            return ResponseEntity
                    .ok()
                    .body(CommonResponse.<String>successOf(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.<String>error(ErrorResponse.of("Image upload failed")));
        }
    }

    @GetMapping("/{userNo}") // 유저 번호에 해당하는 정보 가져오는 API
    public ResponseEntity<UserDTO> getUserByUserNo(@PathVariable("userNo") Long userNo) {
        UserDTO userDTO = userServiceImpl.getUserByUserNo(userNo);

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(userDTO);
    }
}




