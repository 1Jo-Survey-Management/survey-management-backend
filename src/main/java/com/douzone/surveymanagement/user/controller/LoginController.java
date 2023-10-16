package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.dto.NaverAccessTokenExpires;
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
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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

    @PostMapping("/regist")
    public ResponseEntity<CommonResponse> registUser(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String accessToken;
        UserInfo registUser;
        CommonResponse commonResponse;

        accessToken = getAccessTokenFromRequest(request);
        registUser = createRegisteredUser(userInfo, accessToken);
        commonResponse = handleUserRegistration(userInfo, request);

        authenticateUserAfterRegistration(registUser);

        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
        return commonResponseResponseEntity;
    }

    /**
     * @param refreshToken
     * @return
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody UserInfo refreshToken) {

        System.out.println("토큰 만료시 refreshToken : " + refreshToken.getRefreshToken());
        System.out.println("토큰 만료시 oldAccessToken : " + refreshToken.getOldAccessToken());

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&refresh_token=" + refreshToken.getRefreshToken();

        System.out.println("tokenUrl : " + tokenUrl);

        Map<String, String> params = GetAccessToken.getToken(tokenUrl);

        String accessToken = params.get("access_token");
        String expiresIn = params.get("expires_in");
        String viewRefreshToken = params.get("refresh_token");

        System.out.println("accessToken 갱신 : " + accessToken);
        System.out.println("expiresIn : " + expiresIn);
        System.out.println("refreshToken : " + viewRefreshToken);

        UserInfo userInfo;

        userInfo = userService.findUserByUserAccessToken(refreshToken.getOldAccessToken());
        userInfo.setAccessToken(accessToken);
        userInfo.setRefreshToken(refreshToken.getRefreshToken());
        userInfo.setExpiresIn(expiresIn);

        userService.updateAccessToken(userInfo);
        CommonResponse commonResponse = CommonResponse.successOf(userInfo);

        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
        return commonResponseResponseEntity;
    }

    @GetMapping("logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request) {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        String accessToken = getAccessTokenFromRequest(request);

        // accessToken으로 회원의 존재를 먼저 확인한다.
        UserInfo checkUserBeforeDeleteAccessToken = userService.findUserByUserAccessToken(accessToken);

        //--------------------------------------- TODO 1 : 네이버 accesstoken 삭제 메서드 만들기
        if (checkUserBeforeDeleteAccessToken != null) {
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

            String responseBody = response.getBody();

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

            } else {
                return null;
            }
            //---------------------------------------------------------------------------------------

            CommonResponse<String> commonResponse = CommonResponse.successOf(responseBody);

            commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));

            System.out.println("commonResponseResponseEntity : " + commonResponseResponseEntity);

            return commonResponseResponseEntity;

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

        Map<String, String> parms = getAccessTokenUsingCode(clientId, clientSecret, code, state);

        System.out.println("params accessToken : " + parms.get("access_token"));
        NaverUserInfoResponse userInfo = getNaverUserInfo(parms.get("access_token"));

        // provider에서 만료시간을 체크하고 만료시간이 지났을시 자동으로 갱신하게 해준다

        CommonResponse commonResponse = handleUserRegistration(userInfo, parms);

        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
        return commonResponseResponseEntity;
    }


    private CommonResponse handleUserRegistration(UserInfo userInfo, HttpServletRequest request) {
        // Logic for handling user registration
        String accessToken = getAccessTokenFromRequest(request);
        UserInfo registUser = createRegisteredUser(userInfo, accessToken);

        int flag = userService.registUser(registUser); // Register user information in the database

        authenticateUserAfterRegistration(registUser);

        // Create and return the response object
        return CommonResponse.successOf(registUser);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        // Logic to extract the access token from the request
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = "";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        }
        return accessToken;
    }

    private UserInfo createRegisteredUser(UserInfo userInfo, String accessToken) {
        // Logic to create a registered user with the necessary information
        UserInfo userRegist = userService.findUserByUserAccessToken(accessToken);
        userInfo.setUserNo(userRegist.getUserNo());
        userInfo.setUserEmail(userRegist.getUserEmail());
        userInfo.setRefreshToken(userInfo.getRefreshToken());
        userInfo.setAccessToken(accessToken);
        userInfo.setExpiresIn(userInfo.getExpiresIn());
        userInfo.setCreatedAt(userRegist.getCreatedAt());
        return userInfo;
    }

    private void authenticateUserAfterRegistration(UserInfo userInfo) {
        // Logic to authenticate the user after registration
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        CustomAuthentication changeCustomAuthentication = new CustomAuthentication(
                new CustomUserDetails(userInfo.getUserNo(), userInfo.getUserEmail(), userInfo.getUserNickname(), authorities),
                userInfo.getAccessToken()
        );
        SecurityContextHolder.getContext().setAuthentication(changeCustomAuthentication);
    }

    private Map<String, String> getAccessTokenUsingCode(String clientId, String clientSecret, String code, String state) {

        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;
        Map<String, String> params = GetAccessToken.getToken(tokenUrl);
        return params;
    }

    private NaverUserInfoResponse getNaverUserInfo(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        ResponseEntity<NaverUserInfoResponse> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                NaverUserInfoResponse.class
        );
        return response.getBody();
    }

    private CommonResponse handleUserRegistration(NaverUserInfoResponse userInfo, Map<String, String> params) {

        String userEmail = userInfo.getResponse().getEmail();
        UserInfo searchUserInfobyEmail = userService.findUserByUserEmail(userEmail);
        String dbUserEmail = null;
        String dbAccessToken = null;
        long dbUserNo = 0;

        if (searchUserInfobyEmail != null) {
            dbUserEmail = searchUserInfobyEmail.getUserEmail();
            dbAccessToken = searchUserInfobyEmail.getAccessToken();
            dbUserNo = searchUserInfobyEmail.getUserNo();
            System.out.println("(컨트롤러)dbUserEmail : " + dbUserEmail);
        } else {
            System.out.println("이메일로 검색한 회원 존재 하지 않음");
        }

        // AccessToken issue handling
        if (dbUserEmail != null && dbUserEmail.equals(userEmail) && dbAccessToken == null) {
            UserInfo updateUserToken = new UserInfo();
            System.out.println("로그아웃된 회원 다시 로그인");
            updateUserToken.setUserEmail(dbUserEmail);
            updateUserToken.setAccessToken(params.get("access_token"));
            updateUserToken.setRefreshToken(params.get("refresh_token"));
            updateUserToken.setUserNo(dbUserNo);
            int flag = userService.updateAccessToken(updateUserToken);
            System.out.println("Update AccessToken : " + flag);
        } else if (dbUserEmail != null && dbUserEmail.equals(userEmail) && !dbAccessToken.equals(params.get("access_token"))) {
            UserInfo updateUserToken = new UserInfo();
            System.out.println("AccessToken 만료, 토큰 업데이트");
            updateUserToken.setUserNo(dbUserNo);
            updateUserToken.setAccessToken(params.get("access_token"));
            updateUserToken.setRefreshToken(params.get("refresh_token"));
            int flag = userService.updateAccessToken(updateUserToken);
            System.out.println("Update AccessToken : " + flag);
        }

        // accessToken으로 회원이 존재하고 프로필 모두 등록 되었는지 확인
        UserInfo userExistCheck = userService.findUserByUserAccessToken(params.get("access_token"));

        System.out.println("userExistcheck : " + userExistCheck);

        if (userExistCheck != null) {
            System.out.println("userExistCheck : " + userExistCheck);
        }

        // 완료되지 않은 회원가입 정보 확인
        UserInfo userIncompletedCheck = userService.findUserByUserAccessToken(params.get("access_token"));

        // 반환할 객체
        CommonResponse commonResponse;


        // db에 회원이 존재할때
        if (userExistCheck != null) {
            String userNickname = userExistCheck.getUserNickname();
            // 완료된 회원이라면
            if (userNickname != null) {
                System.out.println("userInfo : " + userInfo.getResponse().getEmail());

                UserInfo userCheck = userService.findUserByUserAccessToken(params.get("access_token"));

                System.out.println("유저 확인됨! : " + userCheck);

                // refresh token 과 expires_in 설정
                userCheck.setExpiresIn(params.get("expires_in"));
                userCheck.setRefreshToken(params.get("refresh_token"));

                commonResponse = CommonResponse.successOf(userCheck);

                return commonResponse;
            }
            // 완료되지 않은 회원이라면(프로필 정보 필요함)
            else {
                System.out.println("이미 미완료 회원 정보 있음");
                System.out.println("미완료 회원번호 : " + userIncompletedCheck.getUserNo());

                userIncompletedCheck.setExpiresIn(params.get("expires_in"));
                userIncompletedCheck.setRefreshToken(params.get("refresh_token"));

                commonResponse = CommonResponse.successOf(userIncompletedCheck);

                return commonResponse;
            }

        }
        // db에 회원이 존재하지 않을때(db에 accessToken 기준 미완료 회원도 없을때)
        else {

            System.out.println("미완료 회원 존재 x");

            // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
            Instant instant = Instant.now().plusSeconds(3000);

            // ISO 8601 형식으로 Instant를 문자열로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            String formattedStringExpiresIn = formatter.format(instant);

            System.out.println("미완료회원에 들어가는 만료시간 : " + formattedStringExpiresIn);
            System.out.println("미완료회원에 들어가는 만료시간 : " + formatter);

            UserInfo userRegist = new UserInfo();

            userRegist.setUserEmail(userEmail);
            userRegist.setAccessToken(params.get("access_token"));
            userRegist.setExpiresIn(formattedStringExpiresIn);
            userRegist.setRefreshToken(params.get("refresh_token"));

            // expires in을 컴퓨터 시간에 영향 받지 않고 1시간 이후를 측정한 후 db에 저장
            // provider에서 시간 체크한 후 지났으면 갱신, 안지났으면 말고

            int flag = userService.beforeRegistUser(userRegist);

            System.out.println("미완료 회원 등록 : " + flag);

            commonResponse = CommonResponse.successOf(userRegist);
        }
        return commonResponse;
    }
}
