package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
//import com.douzone.surveymanagement.user.util.GetAccessToken;
//import com.douzone.surveymanagement.user.util.JwtTokenProvider;
//import com.douzone.surveymanagement.user.util.PostRedirector;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Collection;
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

    /**
     * 특정 API 요청 테스트
     * @param request
     * @param response
     * @return 접근 성공 여부
     */
    @PostMapping("/go")
    public ResponseEntity<?> loginGo(HttpServletRequest request, HttpServletResponse response){

        System.out.println("로그인 후 API 요청");

        return ResponseEntity.ok("success");
    }

    //기본적으로 액세스 토큰 등은 이미 저장된 상태고, 들고온 프로필을 넣어 다시 SecurityContextHolder에 넣어준다
    @PostMapping("/regist")
    public ResponseEntity<?> registUser(@RequestBody UserInfo userInfo, HttpServletRequest request){

        // 컨트롤러까지 넘어왔다면 회원인증은 끝난부분이라 가져온 프로필 데이터를
        //db에 저장하고 SecurityContext에 설정해주면 된다.
        System.out.println("첫 로그인 프로필과 함께 회원가입");
        //TODO 1: 가져온 프로필들 db에 저장하기

        int flag = userService.registUser(userInfo);

        System.out.println("업데이트 완료 : " + flag);

        //TODO 2: 회원정보 다시 db에서 가져오기(회원 번호도 가져오기 위해서)

        request.getHeader("")

        //TODO 3 :  CustomAuthentication 객체를 새로 생성하여 UserDetails를 업데이트
        CustomAuthentication customAuthentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        CustomAuthentication changeCustomAuthentication = new CustomAuthentication(
                // 이 아래 부분 userInfo.getUserNo로 하면 아무것도 없다 위는 프로필에서 가져온것이기 때문, 따라서 투두 2번에서 가져온 회원 정보로
                // 아래것들을 채워줘야한다.
                new CustomUserDetails(userInfo.getUserNo(), userInfo.getUserEmail(), userInfo.getUserNickname(),
                        customAuthentication.getAuthorities()),
                userInfo.getAccessToken()
        );

        //TODO 4 : 변경된 CustomAuthentication 객체를 SecurityContext에 다시 설정
        SecurityContextHolder.getContext().setAuthentication(changeCustomAuthentication);

        return ResponseEntity.ok("success");
    }

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

        String accessToken = params.get("access_token");
        String expiresIn = params.get("expires_in");
        String refreshToken = params.get("refresh_token");

        System.out.println("(LoginController) expires in : " + expiresIn);
        System.out.println("(LoginController) refreshToken : " + refreshToken);

        RestTemplate restTemplate = new RestTemplate();
        /**
         * HTTP 요청 헤더 설정
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

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

        if(userEmail.isEmpty()){

        System.out.println("userInfo : " + userInfo.getResponse().getEmail());
        System.out.println("response : "+ response.getStatusCode());

        // 나중에 토큰 부분으로 찾는거 수정하기
        UserInfo userCheck = userService.findUserByUserAccessToken("abc");


        //회원이 존재 할 때
            System.out.println("유저 확인됨! : " + userCheck);

            //리턴 url에 토큰이랑 회원Pk 반환
            return "redirect:http://localhost:3000/survey/main?accessToken=abc&&userNo=" + userCheck.getUserNo();

        }
        //회원이 존재 하지 않을때
        else{
            // 회원 가입 실시. 첫 로그인 프로필 등록
            // 데이터 베이스에 이메일과 액세스 토큰만 일단! 저장하여 만들어 놓고
            //TODO : 이메일,액세스 토큰으로만 회원가입 해놓기

            UserInfo userRegist = new UserInfo();

            userRegist.setUserEmail(userEmail);
            userRegist.setAccessToken("abc");

            int flag = userService.beforeRegistUser(userRegist);

            System.out.println("미완료 회원 등록 : " + flag);

            // 토큰 생성 첫 프로필 모달 있는 페이지로 전달
            // 이후 요청으로 돌아올때 회원가입 확정과 함게 인가 완료

            System.out.println("유저 없음!");

            return "redirect:http://localhost:3000?accessToken=abc";
        }



    }



}
