package com.douzone.surveymanagement.user.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 다른 메서드 test api 입니다. 차후 제거할 예정입니다
 * @author 김선규
 */
@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/go")
    public ResponseEntity<?> loginGo(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("로그인 후 API 요청");

        return ResponseEntity.ok("success");
    }

}
