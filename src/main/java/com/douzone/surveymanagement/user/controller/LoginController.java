package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserServiceImpl userService;

    /**
     * 특정 API 요청 테스트
     *
     * @param request
     * @param response
     * @return 접근 성공 여부
     */
    @PostMapping("/go")
    public ResponseEntity<?> loginGo(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("로그인 후 API 요청");

        return ResponseEntity.ok("success");
    }

    //기본적으로 액세스 토큰 등은 이미 저장된 상태고, 들고온 프로필을 넣어 다시 SecurityContextHolder에 넣어준다
    @PostMapping("/regist")
    public ResponseEntity<CommonResponse> registUser(@RequestBody UserInfo userInfo, HttpServletRequest request) {

        // 컨트롤러까지 넘어왔다면 회원인증은 끝난부분이라 가져온 프로필 데이터를
        //db에 저장하고 SecurityContext에 설정해주면 된다.
        System.out.println("첫 로그인 프로필과 함께 회원가입");

        System.out.println("가져온 프로필 : " + userInfo);

        //TODO 1: 가져온 프로필들 db에 저장하기

        UserInfo userRegist = new UserInfo();

        userRegist = userService.findUserByUserAccessToken("abc");

        userInfo.setUserNo(userRegist.getUserNo());

        int flag = userService.registUser(userInfo);

        System.out.println("업데이트 완료 : " + flag);

        //TODO 2: 회원정보 다시 db에서 가져오기(회원 번호도 가져오기 위해서)

        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = "";
        CommonResponse commonResponse = null;
        UserInfo userAllInfo = new UserInfo();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분
            System.out.println("헤더에서 accessToken : " + accessToken);

            userAllInfo = userService.findUserByUserAccessToken(accessToken);

            System.out.println("컨트롤러 userAllInfo : " + userAllInfo);

            commonResponse = CommonResponse.successOf(userAllInfo);

            System.out.println("컨트롤러 commonRespnse : " + commonResponse.getContent());
        }


        //TODO 3 :  CustomAuthentication 객체를 새로 생성하여 UserDetails를 업데이트

        System.out.println("customAuthetication : " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        CustomAuthentication changeCustomAuthentication = new CustomAuthentication(
                // 이 아래 부분 userInfo.getUserNo로 하면 아무것도 없다 위는 프로필에서 가져온것이기 때문, 따라서 투두 2번에서 가져온 회원 정보로
                // 아래것들을 채워줘야한다.
                new CustomUserDetails(userAllInfo.getUserNo(), userAllInfo.getUserEmail(), userAllInfo.getUserNickname(),
                        authorities),
                "abc"
        );
        // 나중에 accessToken은 대용인 abc 대신으로 바꾸어주어야함

        System.out.println("changeCustomAuthentication : " + changeCustomAuthentication);

        //TODO 4 : 변경된 CustomAuthentication 객체를 SecurityContext에 다시 설정
        SecurityContextHolder.getContext().setAuthentication(changeCustomAuthentication);


        System.out.println("회원 가입 및 contextHolder 설정 완료");

        return ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
    }

    /**
     * 네이버 로그인 서비스 요청후 CallBack url 리다이렉트 받아서 토큰 처리 메서드
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("oauth2/code/naver")
    public String naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        /**
         *  클라이언트 ID와 시크릿을 설정
         */

        System.out.println("네이버 콜백 들어옴");

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        /**
         * Authorization Code를 사용하여 액세스 토큰 요청
         */
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;

        System.out.println("tokenUrl : " + tokenUrl);

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

        // accessToken으로 회원이 존재하고 프로필 모두 등록 되었는지 확인
        UserInfo userExistCheck = userService.findUserByUserAccessToken("abc");

        System.out.println("userExistCheck : " + userExistCheck);


        // 회원 가입 실시. 첫 로그인 프로필 등록
        // 데이터 베이스에 이메일과 액세스 토큰만 일단! 저장하여 만들어 놓고
        // 토큰 생성 첫 프로필 모달 있는 페이지로 전달
        // 이후 요청으로 돌아올때 회원가입 확정과 함게 인가 완료
        //TODO : 이메일,액세스 토큰으로만 회원가입 해놓기

        // 완료되지 않은 회원가입 정보 확인
        UserInfo userIncompletedCheck = new UserInfo();
        userIncompletedCheck = userService.findUserByUserAccessToken("abc");

        // db에 회원이 존재할때
        if (userExistCheck != null) {
            // 완료된 회원이라면
            if (!userExistCheck.getUserNickname().isEmpty()) {
                System.out.println("userInfo : " + userInfo.getResponse().getEmail());
                System.out.println("response : " + response.getStatusCode());

                UserInfo userCheck = userService.findUserByUserAccessToken("abc");

                System.out.println("유저 확인됨! : " + userCheck);

                //리턴 url에 토큰이랑 회원Pk 반환
                return "redirect:http://localhost:3000/survey/main?accessToken=abc&&userNo=" + userCheck.getUserNo();
            }
            // 완료되지 않은 회원이라면(프로필 정보 필요함)
            else{
                    System.out.println("이미 미완료 회원 정보 있음");
                    System.out.println("미완료 회원번호 : " + userIncompletedCheck.getUserNo());

                    // 이후 요청 : 토큰 + 회원pk = 정상 로그인 , 토큰 = 미완료 회원 등록 진행
                    return "redirect:http://localhost:3000?accessToken=abc";
            }

        }
        // db에 회원이 존재하지 않을때(db에 accessToken 기준 미완료 회원도 없을때)
        else {

            System.out.println("미완료 회원 존재 x");

            UserInfo userRegist = new UserInfo();

            userRegist.setUserEmail(userEmail);
            userRegist.setAccessToken("abc");

            int flag = userService.beforeRegistUser(userRegist);

            System.out.println("미완료 회원 등록 : " + flag);

            // 이후 요청 : 토큰 + 회원pk = 정상 로그인 , 토큰 = 미완료 회원 등록 진행
            return "redirect:http://localhost:3000?accessToken=abc";

        }

    }
}
