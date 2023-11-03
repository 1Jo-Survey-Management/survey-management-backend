package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import com.douzone.surveymanagement.user.util.NTPTimeFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.ZonedDateTime;
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
@Slf4j
public class LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserServiceImpl userService;

    public ResponseEntity<CommonResponse> commonResponseResponseEntity;

    /**
     * 회원 프로필 가져오는 메서드입니다.
     * @param request
     * @return 회원정보
     * @author 김선규
     */
    @PostMapping("user")
    public ResponseEntity<CommonResponse> userProfile(HttpServletRequest request) {
        String accessToken = getAccessTokenFromRequest(request);

        UserInfo returnUser = userService.findUserByUserAccessToken(accessToken);

        CommonResponse commonResponse = CommonResponse.successOf(returnUser);
        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
        return commonResponseResponseEntity;
    }

    /**
     * 회원 가입하는 메서드입니다.
     * @param userInfo
     * @param request
     * @return ResponseEntity<CommonResponse>
     * @author 김선규
     */
    @PostMapping("/regist")
    public ResponseEntity<CommonResponse> registUser(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String accessToken;
        UserInfo registUser;
        CommonResponse commonResponse;

        accessToken = getAccessTokenFromRequest(request);
        registUser =    createRegisteredUser(userInfo, accessToken);
        commonResponse = handleUserRegistration(userInfo, request);

        authenticateUserAfterRegistration(registUser);

        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
        return commonResponseResponseEntity;
    }

    /**
     * 네이버 로그인 서비스 요청후 CallBack uri를 받아서 회원가입 진행하는 메서드입니다
     *
     * @param code
     * @param state
     * @return ResponseEntity
     * @author 김선규
     */
    @GetMapping("oauth2/code/naver")
    public ResponseEntity<CommonResponse> naverCallback(@RequestParam(name = "code", required = false) String code,
                                                        @RequestParam(name = "state", required = false) String state) {
        log.debug("Naver CallBack Uri");
        System.out.println("Naver CallBack Uri");
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        Map<String, String> parms = getAccessTokenUsingCode(clientId, clientSecret, code, state);

        log.debug("params accessToken : " + parms.get("access_token"));
        System.out.println("params accessToken : " + parms.get("access_token"));
        NaverUserInfoResponse userInfo = getNaverUserInfo(parms.get("access_token"));

        CommonResponse commonResponse = handleUserRegistration(userInfo, parms);

        commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.ofNullable(commonResponse));
        return commonResponseResponseEntity;
    }

    /**
     * 회원가입을 진행하는 메서드입니다
     * @param userInfo
     * @param request
     * @return CommonResponse
     * @author 김선규
     */
    private CommonResponse handleUserRegistration(UserInfo userInfo, HttpServletRequest request) {

        String accessToken = getAccessTokenFromRequest(request);
        UserInfo registUser = createRegisteredUser(userInfo, accessToken);

        userService.registUser(registUser);

        authenticateUserAfterRegistration(registUser);

        return CommonResponse.successOf(registUser);
    }

    /**
     * request header에서 accessToken을 추출하는 메서드입니다.
     * @param request
     * @return accessToken
     * @author 김선규
     */
    private String getAccessTokenFromRequest(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = "";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        }
        return accessToken;
    }

    /**
     * 등록된 회원의 정보를 가져오는 메서드입니다.
     * @param userInfo
     * @param accessToken
     * @return userInfo
     * @author 김선규
     */
    private UserInfo createRegisteredUser(UserInfo userInfo, String accessToken) {

        UserInfo userRegist = userService.findUserByUserAccessToken(accessToken);
        userInfo.setUserNo(userRegist.getUserNo());
        userInfo.setUserEmail(userRegist.getUserEmail());
        userInfo.setRefreshToken(userInfo.getRefreshToken());
        userInfo.setAccessToken(accessToken);
        userInfo.setExpiresIn(userInfo.getExpiresIn());
        userInfo.setCreatedAt(userRegist.getCreatedAt());
        return userInfo;
    }

    /**
     * 완료된 회원가입에 대하여 인증 객체와 ContextHolder에 등록해주는 메서드입니다.
     * @param userInfo
     * @author 김선규
     */
    private void authenticateUserAfterRegistration(UserInfo userInfo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        CustomAuthentication changeCustomAuthentication = new CustomAuthentication(
                new CustomUserDetails(userInfo.getUserNo(), userInfo.getUserEmail(), userInfo.getUserNickname(), userInfo.getUserGender(), userInfo.getUserBirth(), userInfo.getUserImage(), authorities),
                userInfo.getAccessToken()
        );
        SecurityContextHolder.getContext().setAuthentication(changeCustomAuthentication);
    }

    /**
     * 네이버 Code Url로 accessToken, refreshToken, expiresIn 가져오는 메서드입니다.
     * @param clientId
     * @param clientSecret
     * @param code
     * @param state
     * @return params
     * @author 김선규
     */
    private Map<String, String> getAccessTokenUsingCode(String clientId, String clientSecret, String code, String state) {

        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;
        Map<String, String> params = GetAccessToken.getToken(tokenUrl);
        return params;
    }

    /**
     * 네이버 OAuth에서 프로필 연동으로 정보 가져오는 메서드입니다.
     * @param accessToken
     * @return UserInfo
     * @author 김선규
     */
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

    /**
     * 프로필로 들고온 회원 유저 정보로 회원가입 완료하는 메서드입니다.
     * @param userInfo
     * @param params
     * @return commonResponse
     * @author 김선규
     */
    private CommonResponse handleUserRegistration(NaverUserInfoResponse userInfo, Map<String, String> params) {

        String userEmail = userInfo.getResponse().getEmail();
        String newAccessToken = params.get("access_token");
        UserInfo searchUserInfobyEmail = userService.findUserByUserEmail(userEmail);
        String dbUserEmail = null;
        String dbAccessToken = null;
        long dbUserNo = 0;

        if (searchUserInfobyEmail != null) {
            dbUserEmail = searchUserInfobyEmail.getUserEmail();
            dbAccessToken = searchUserInfobyEmail.getAccessToken();
            dbUserNo = searchUserInfobyEmail.getUserNo();

        } else {
            log.error("이메일로 검색한 회원 존재 하지 않음");
        }

        if (dbUserEmail != null && dbUserEmail.equals(userEmail) && (dbAccessToken == null || !dbAccessToken.equals(newAccessToken)) ) {
            UserInfo updateUserToken = new UserInfo();
            log.debug("로그아웃된 회원 다시 로그인");
            System.out.println("로그아웃된 회원 다시 로그인");

            NTPTimeFetcher ntpTimeFetcher = new NTPTimeFetcher();
            ZonedDateTime koreaTime = ZonedDateTime.parse(ntpTimeFetcher.getFormattedKoreaTimeWithExpiration(50));

            // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
            ZonedDateTime newExpiresTime = koreaTime.plusMinutes(50);

            // yyyy-MM-dd HH:mm:ss 형식으로 ZonedDateTime를 문자열로 변환
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedStringExpiresIn = newExpiresTime.format(formatter2);


            updateUserToken.setUserEmail(dbUserEmail);
            updateUserToken.setAccessToken(params.get("access_token"));
            updateUserToken.setRefreshToken(params.get("refresh_token"));
            updateUserToken.setExpiresIn(formattedStringExpiresIn);
            updateUserToken.setUserNo(dbUserNo);
            int flag = userService.updateAccessToken(updateUserToken);
            log.debug("Update AccessToken : " + flag);
        }

        // accessToken으로 회원이 존재하고 프로필 모두 등록 되었는지 확인
        UserInfo userExistCheck = userService.findUserByUserAccessToken(params.get("access_token"));

        log.debug("userExistcheck : " + userExistCheck);
        System.out.println("컨트롤러 userExistcheck : " + userExistCheck);

        // 완료되지 않은 회원가입 정보 확인
        UserInfo userIncompletedCheck = userService.findUserByUserAccessToken(params.get("access_token"));

        // 반환할 객체
        CommonResponse commonResponse;


        // db에 회원이 존재할때
        if (userExistCheck != null) {
            String userNickname = userExistCheck.getUserNickname();

            // 완료된 회원이라면
            if (userNickname != null) {

                log.debug("유저 확인됨! userEmail : " + userInfo.getResponse().getEmail());
                System.out.println("유저 확인됨! userEmail : " + userInfo.getResponse().getEmail());

                UserInfo userCheck = userService.findUserByUserAccessToken(params.get("access_token"));

                userCheck.setExpiresIn(userCheck.getExpiresIn());
                userCheck.setRefreshToken(params.get("refresh_token"));

                commonResponse = CommonResponse.successOf(userCheck);

                return commonResponse;
            }
            // 완료되지 않은 회원이라면(프로필 정보 필요함)
            else {
                log.debug("(회원가입 중)미완료 회원번호 : " + userIncompletedCheck.getUserNo());
                System.out.println("(회원가입 중)미완료 회원번호 : " + userIncompletedCheck.getUserNo());

                userIncompletedCheck.setExpiresIn(params.get("expires_in"));
                userIncompletedCheck.setRefreshToken(params.get("refresh_token"));

                System.out.println("유효기간 이색기 확인해보자 : " + params.get("expires_in"));


                commonResponse = CommonResponse.successOf(userIncompletedCheck);

                return commonResponse;
            }

        }
        // db에 회원이 존재하지 않을때(db에 accessToken 기준 미완료 회원도 없을때)
        else {

            log.debug("미완료 회원도 존재 x");
            System.out.println("미완료 회원도 존재x");

            // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
            NTPTimeFetcher ntpTimeFetcher = new NTPTimeFetcher();
            String koreaTime = ntpTimeFetcher.getFormattedKoreaTimeWithExpiration(50);

            System.out.println("파싱된 NTP 타임 : " + koreaTime);

            UserInfo userRegist = new UserInfo();

            userRegist.setUserEmail(userEmail);
            userRegist.setAccessToken(params.get("access_token"));
            userRegist.setExpiresIn(koreaTime);
            userRegist.setRefreshToken(params.get("refresh_token"));

            int flag = userService.beforeRegistUser(userRegist);

            System.out.println("미완료 회원 accessToken : " + params.get("access_token"));

            log.debug("미완료 회원 등록 : " + flag);

            commonResponse = CommonResponse.successOf(userRegist);
        }
        return commonResponse;
    }

}
