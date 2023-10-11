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

    public ResponseEntity<CommonResponse> commonResponseResponseEntity;

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

        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = "";
        CommonResponse commonResponse = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분
            System.out.println("헤더에서 accessToken : " + accessToken);

        }

        //TODO 1: 가져온 프로필들 db에 저장하기

        UserInfo userRegist = new UserInfo();

        userRegist = userService.findUserByUserAccessToken(accessToken);

        userInfo.setUserNo(userRegist.getUserNo());

        int flag = userService.registUser(userInfo);

        System.out.println("업데이트 완료 : " + flag);

        //TODO 2: 회원정보 다시 db에서 가져오기(회원 번호도 가져오기 위해서)


        UserInfo userAllInfo = new UserInfo();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

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


    @GetMapping("logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request){

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분
            System.out.println("헤더에서 accessToken : " + accessToken);

        }
        // accessToken으로 회원의 존재를 먼저 확인한다.
        UserInfo checkUserBeforeDeleteAccessToken = userService.findUserByUserAccessToken(accessToken);

        if(checkUserBeforeDeleteAccessToken!=null) {
            String checkDeleteAccessToken = checkUserBeforeDeleteAccessToken.getAccessToken();
            long userNo = checkUserBeforeDeleteAccessToken.getUserNo();
            System.out.println("checkUserBeforeDeleteAccessToken : " + checkDeleteAccessToken);


            // RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            // 요청을 보낼 URL 정의
            String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete";

            // 요청 바디 데이터 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String requestBody =
                    "service_provider=NAVER" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&access_token=" + accessToken;

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            //---------------------------------------
            // 응답 데이터 가져오기
            String responseBody = response.getBody();

            // 응답 상태 코드 확인
            int statusCode = response.getStatusCodeValue();

            System.out.println("응답 데이터: " + responseBody);
            System.out.println("상태 코드: " + statusCode);

            if (statusCode == 200) {
                // 성공하면 db에서도 accessToken을 삭제한다
                UserInfo userInfo = new UserInfo();
                userInfo.setUserNo(userNo);
                userInfo.setAccessToken(accessToken);

                int flag = userService.deleteAccessToken(userInfo);

                System.out.println("db에서도 삭제 : " + flag);

                CommonResponse<String> commonResponse = CommonResponse.successOf(responseBody);

                commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));

                System.out.println("commonResponseResponseEntity : " + commonResponseResponseEntity);

                return commonResponseResponseEntity;

            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 네이버 로그인 서비스 요청후 CallBack url 리다이렉트 받아서 토큰 처리 메서드
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("oauth2/code/naver")
    public ResponseEntity<CommonResponse> naverCallback(@RequestParam(name = "code", required = false) String code,
                                @RequestParam(name = "state", required = false) String state) {

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
            String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
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

            UserInfo searchUserInfobyEmail = userService.findUserByUserEmail(userEmail);
            String dbUserEmail = null;
            String dbAccessToken = null;
            long dbUserNo = 0;

            if(searchUserInfobyEmail!=null){
                dbUserEmail = searchUserInfobyEmail.getUserEmail();
                dbAccessToken = searchUserInfobyEmail.getAccessToken();
                dbUserNo = searchUserInfobyEmail.getUserNo();
                System.out.println("(컨트롤러)dbUserEmail : " + dbUserEmail);
            }

            // 여기서 userEmail로 네이버 로그인과 회원존재하는지를 확인해서 이메일==회원 이메일 이라서 회원 존재하는데
            // userExistCheck가 false이다. 그렇다면 로그아웃해서 accessToken만 없는 거임 그래서 토큰만 업데이트 해줌

            // db에 회원이 존재하고, accessToken이 없으면 새로 받은 토큰 다시 db에 넣어줌
            if(dbUserEmail!= null && dbUserEmail.equals(userEmail) && dbAccessToken==null){
                UserInfo updateUserToken = new UserInfo();

                System.out.println("로그아웃된 회원 다시 로그인");

                updateUserToken.setUserEmail(dbUserEmail);
                updateUserToken.setAccessToken(accessToken);
                updateUserToken.setUserNo(dbUserNo);
                int flag = userService.updateAccessToken(updateUserToken);

                System.out.println("Update AccessToken : " + flag);
            }
            // db에 회원이 존재하고, accessToken이 만료됐다면 새로운 토큰 다시 db에 넣어줌
            else if (dbUserEmail!= null && dbUserEmail.equals(userEmail) && !dbAccessToken.equals(accessToken)){
                UserInfo updateUserToken = new UserInfo();

                System.out.println("AccessToken 만료, 토큰 업데이트ㅇㅇㅇ");

                updateUserToken.setUserNo(dbUserNo);
                updateUserToken.setAccessToken(accessToken);
                int flag = userService.updateAccessToken(updateUserToken);

                System.out.println("Update AccessToken : " + flag);
            }

        // accessToken으로 회원이 존재하고 프로필 모두 등록 되었는지 확인
        UserInfo userExistCheck = userService.findUserByUserAccessToken(accessToken);

        if(userExistCheck!=null){
            System.out.println("userExistCheck : " + userExistCheck);
        }

            // 회원 가입 실시. 첫 로그인 프로필 등록
            // 데이터 베이스에 이메일과 액세스 토큰만 일단! 저장하여 만들어 놓고
            // 토큰 생성 첫 프로필 모달 있는 페이지로 전달
            // 이후 요청으로 돌아올때 회원가입 확정과 함게 인가 완료
            //TODO : 이메일,액세스 토큰으로만 회원가입 해놓기

            // 완료되지 않은 회원가입 정보 확인
            UserInfo userIncompletedCheck = new UserInfo();
            userIncompletedCheck = userService.findUserByUserAccessToken(accessToken);

            // 반환할 객체
            CommonResponse commonResponse ;

            String userNickname = userExistCheck.getUserNickname();

            // db에 회원이 존재할때
            if (userExistCheck != null) {
                // 완료된 회원이라면
                if (userNickname!=null) {
                    System.out.println("userInfo : " + userInfo.getResponse().getEmail());
                    System.out.println("response : " + response.getStatusCode());

                    UserInfo userCheck = userService.findUserByUserAccessToken(accessToken);

                    System.out.println("유저 확인됨! : " + userCheck);

                    commonResponse = CommonResponse.successOf(userCheck);


                    //리턴 url에 토큰이랑 회원Pk 반환
                    commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
                    return commonResponseResponseEntity;
                }
                // 완료되지 않은 회원이라면(프로필 정보 필요함)
                else {
                    System.out.println("이미 미완료 회원 정보 있음");
                    System.out.println("미완료 회원번호 : " + userIncompletedCheck.getUserNo());

                    commonResponse = CommonResponse.successOf(userIncompletedCheck);

                    // 이후 요청 : 토큰 + 회원pk = 정상 로그인 , 토큰 = 미완료 회원 등록 진행

                    commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
                    return commonResponseResponseEntity;
                }

            }
            // db에 회원이 존재하지 않을때(db에 accessToken 기준 미완료 회원도 없을때)
            else {

                System.out.println("미완료 회원 존재 x");

                UserInfo userRegist = new UserInfo();

                userRegist.setUserEmail(userEmail);
                userRegist.setAccessToken(accessToken);

                int flag = userService.beforeRegistUser(userRegist);

                System.out.println("미완료 회원 등록 : " + flag);

                commonResponse = CommonResponse.successOf(userRegist);

                // 이후 요청 : 토큰 + 회원pk = 정상 로그인 , 토큰 = 미완료 회원 등록 진행
                commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
                return commonResponseResponseEntity;
            }

        }

}
