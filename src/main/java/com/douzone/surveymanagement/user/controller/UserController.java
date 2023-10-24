package com.douzone.surveymanagement.user.controller;
import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 다른 메서드 test api 입니다. 차후 제거할 예정입니다
 * @author 김선규
 */
@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    public ResponseEntity<CommonResponse> commonResponseResponseEntity;

    @PostMapping("/go")
    public ResponseEntity<?> loginGo(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("로그인 후 API 요청");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // UserDetails 객체로부터 필요한 정보 추출
        String userNickname = userDetails.getNickname();
        int userNo = (int) userDetails.getUserNo();
        String userEmail = userDetails.getUserEmail();
        String userGender = userDetails.getUserGender();
        Date userBirth = userDetails.getUserBirth();
        String userImage = userDetails.getUserImage();

        UserInfo userInfo = new UserInfo();

        userInfo.setUserNickname(userNickname);
        userInfo.setUserNo(userNo);
        userInfo.setUserEmail(userEmail);
        userInfo.setUserGender(userGender);
        userInfo.setUserBirth(userBirth);
        userInfo.setUserImage(userImage);


        CommonResponse commonResponse = CommonResponse.successOf(userInfo);
        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
        return commonResponseResponseEntity;
    }

}
