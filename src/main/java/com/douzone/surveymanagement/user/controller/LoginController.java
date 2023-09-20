package com.douzone.surveymanagement.user.controller;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;

import com.douzone.surveymanagement.user.util.GetAccessToken;


import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 * 로그인 관련 Controller 입니다
 * @author 김선규
 * @since 1.0
 */
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private static final int DEFAULT_FALSE_FLAG = 0;
    private final UserServiceImpl userService;
//    private final JwtTokenProvider jwtTokenProvider;



    // 타임리프로 뷰 리졸버 설정하면 코드 아주 간단해짐.. 고민해봐야할듯
    @GetMapping("/log")
    public ResponseEntity<byte[]> loginGo() throws IOException {
        System.out.println("로그인고 들어옴");
        Resource resource = new ClassPathResource("static/login.html");
        byte[] htmlBytes = Files.readAllBytes(resource.getFile().toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlBytes);
    }

    @GetMapping("/go")
    public void Check() {
        System.out.println("체크");

    }

    @GetMapping("/check")
    public ResponseEntity<byte[]> chch() throws IOException {

        System.out.println("석세스리디렉션 체크");
        Resource resource = new ClassPathResource("static/go.html");
        byte[] htmlBytes = Files.readAllBytes(resource.getFile().toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlBytes);
    }



    /**
     * 네이버 로그인 서비스 요청후 CallBack url 리다이렉트 받아서 토큰 처리 메서드
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/oauth2/callback/naver")
    public void naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        /**
         *  클라이언트 ID와 시크릿을 설정
         */

        System.out.println("심지어 리다이렉트 됨 : " + code + state);

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        /**
         * Authorization Code를 사용하여 액세스 토큰 요청
         */
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;

        System.out.println(tokenUrl);

        /**
         * 네이버 API 엔드포인트 URL
         */
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> params = GetAccessToken.getToken(tokenUrl);

        String AccessCode = params.get("access_token");

        RestTemplate restTemplate = new RestTemplate();
        /**
         * HTTP 요청 헤더 설정
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + AccessCode);

        System.out.println("headers : " + headers);

        /**
         * HTTP 요청 엔터티 생성
         */
        HttpEntity<String> entity = new HttpEntity<>(headers);

        /**
         * HTTP GET 요청
         */
        ResponseEntity<NaverUserInfoResponse> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                NaverUserInfoResponse.class
        );

        /**
         * API 응답 데이터를 NaverUserInfoResponse 객체로 변환
         */
        NaverUserInfoResponse userInfo = response.getBody();

        String userEmail = userInfo.getResponse().getEmail();

        System.out.println("userInfo : " + userInfo.getResponse().getEmail());
        System.out.println("response : "+ response.getStatusCode());

        int flag = userService.loginCheck(userEmail);

        if(flag!=DEFAULT_FALSE_FLAG){
            // 회원 정보에 회원 확인 됨.
            // 토큰 생성 첫 프로필 모달 없는 페이지로 전달

//            String jwtToken = jwtTokenProvider.generateToken(userEmail);

//            System.out.println(jwtToken);

//            PostRedirector postRedirector = new PostRedirector();

//            postRedirector.sendTokenToReact(jwtToken);

            System.out.println("유저 확인됨!");
//            return "redirect:http://localhost:8080/";
        }else{
            // 회원 가입 실시. 첫 로그인 프로필 등록
            // 토큰 생성 첫 프로필 모달 있는 페이지로 전달
            System.out.println("유저 없음!");
        }

//         return "/";

    }




}
