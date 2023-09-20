package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 로그인 관련 Controller 입니다
 * @author 김선규
 * @since 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }


    /**
     * 네이버 로그인 서비스 요청후 CallBack url 리다이렉트 받아서 토큰 처리 메서드
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/naver/callback")
    public String naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        /**
         *  클라이언트 ID와 시크릿을 설정
         */
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

        System.out.println("userInfo : " + userInfo.getResponse().getEmail());
        System.out.println("response : "+ response.getStatusCode());


        return "redirect:http://localhost:3000/survey/main";

    }



}
