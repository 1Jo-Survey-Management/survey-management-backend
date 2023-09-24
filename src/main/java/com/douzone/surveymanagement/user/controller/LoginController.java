package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
//import com.douzone.surveymanagement.user.util.GetAccessToken;
//import com.douzone.surveymanagement.user.util.JwtTokenProvider;
//import com.douzone.surveymanagement.user.util.PostRedirector;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;

/**
 * 로그인 관련 Controller 입니다
 * @author 김선규
 * @since 1.0.0
 */
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private static final int DEFAULT_FALSE_FLAG = 0;

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserServiceImpl userService;
//    private final JwtTokenProvider jwtTokenProvider;
//private  final RestTemplate restTemplate = new RestTemplate();
//
//
//
//    private HttpSession session;

//    @GetMapping("login/oauth2/code/{registrationId}")
//    public String oauth2Login(HttpServletRequest request, HttpServletResponse response) {
////        OAuth2AccessToken accessToken = ; // OAuth 로그인으로 얻은 Access Token
////        session.setAttribute("access_token", accessToken.getTokenValue());
//
//        // 세션 ID와 Access Token을 연결하고 저장할 수도 있습니다.
////         session.setAttribute("access_token_" + session.getId(), accessToken.getTokenValue());
//
//        // 다른 작업 수행 및 리다이렉션
//        // ...
//
//        System.out.println("여기 지나감 ");
//        return "";
//    }

//    // 네이버 로그인으로부터 Access Token을 얻는 코드
//    public OAuth2AccessToken getNaverAccessToken(Authentication authentication) {
//        if (authentication instanceof OAuth2Authentication) {
//            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
//            OAuth2User oauth2User = (OAuth2User) oauth2Authentication.getPrincipal();
//            OAuth2AccessToken accessToken = oauth2User.getAttribute("access_token");
//            return accessToken;
//        }
//        return null;
//    }



    @GetMapping("/go")
    public ResponseEntity<?> loginGo(HttpServletRequest request, HttpServletResponse response){



        System.out.println("로그인 요청");

        return ResponseEntity.ok("success");
    }


//        @Value("${naver.client-id}")
//        private String clientId;
//
//        @Value("${naver.redirect-uri}")
//        private String redirectUri;

//    @GetMapping("oauth2/code/naver1")
//    public ResponseEntity<?> naverCallback1(@RequestParam(name = "access_token", required = false) String accessToken, @RequestParam(name = "state", required = false) String state) {
//        if (accessToken != null) {
//            // access_token을 받아서 필요한 작업을 수행합니다.
////            return "callback";
//            System.out.println("access Token 있음");
//            return ResponseEntity.ok("success");
//
//
//        }
//        else {
//            String authorizationUri = "https://nid.naver.com/oauth2.0/authorize";
//            String stateParam = "YOUR_STATE"; // CSRF 공격 방지를 위한 랜덤한 문자열
//
//
//
//            String redirectUrl = authorizationUri + "?client_id=" + "ukwEecKhMrJzOdjwpJfB" +
//                    "&response_type=token&redirect_uri=" + "http://localhost:8080/login/oauth2/code/naver" +
//                    "&state=" + stateParam;
//
//            System.out.println(redirectUrl);
//
//            return ResponseEntity.ok("success");
//        }

//        else {
//            String authorizationUri = "https://nid.naver.com/oauth2.0/authorize";
//            String stateParam = "YOUR_STATE"; // CSRF 공격 방지를 위한 랜덤한 문자열
//
//            URI authorizationUrl = URI.create(authorizationUri + "?client_id=" + "ukwEecKhMrJzOdjwpJfB" +
//                    "&response_type=token&redirect_uri=" + "http://localhost:8080/login/oauth2/code/naver" +
//                    "&state=" + stateParam);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            System.out.println("headers : " + headers);
//
//            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, authorizationUrl);
//
//            ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
//
//            System.out.println("responseEntity : " + responseEntity);
//
//            // responseEntity에서 리디렉션 URL을 추출하여 사용자를 네이버 로그인 페이지로 리디렉션합니다.
//            HttpHeaders responseHeaders = responseEntity.getHeaders();
//            String redirectUrl = responseHeaders.getFirst(HttpHeaders.LOCATION);
//
//            System.out.println("redirectUrl : "+redirectUrl);
//
//            return ResponseEntity.ok(redirectUrl);
//        }
//    }



    /**
     * 네이버 로그인 서비스 요청후 CallBack url 리다이렉트 받아서 토큰 처리 메서드
     * @param code
     * @param state
     * @return
     */
    @GetMapping("oauth2/code/naver")
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
            return "redirect:http://localhost:3000/survey/main";
        }else{
            // 회원 가입 실시. 첫 로그인 프로필 등록
            // 토큰 생성 첫 프로필 모달 있는 페이지로 전달
            System.out.println("유저 없음!");
        }

         return "";

    }



}
