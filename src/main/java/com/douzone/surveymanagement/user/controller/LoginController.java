package com.douzone.surveymanagement.user.controller;

import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.user.dto.NaverClientProperties;
import com.douzone.surveymanagement.user.dto.NaverUserInfoResponse;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.dto.request.UserModifyDTO;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 로그인 관련 Controller 입니다
 * @author 김선규
 * @since 1.0.0
 */
@Controller
@RequestMapping("/api/oauthLogin")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserServiceImpl userService;
    private final NaverClientProperties naverClientProperties;
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
        commonResponseResponseEntity = ResponseEntity.of(Optional.of(commonResponse));
        return commonResponseResponseEntity;
    }

    @PostMapping("/check-duplicate-nickname")
    public ResponseEntity<String> getUserByUserNickname(@RequestBody UserModifyDTO userModifyDTO) {
        boolean isDuplicate = userService.duplicateUsername(userModifyDTO);
        
        if(isDuplicate) {
            return ResponseEntity.ok("Nickname is not available");
        }

        return ResponseEntity.ok("Nickname is available");
    }

    /**
     * 회원가입 중간 취소에 따른 가입 미완료 회원 삭제
     * @param userNo
     * @return void
     * @author 김선규
     */
    @GetMapping("cancel")
    public ResponseEntity<CommonResponse> loginCancel(@RequestParam(name = "userNo") String userNo) {
        boolean isCancelSuccessed = userService.loginCancel(userNo);
        CommonResponse commonResponse = CommonResponse.successOf(isCancelSuccessed);
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
                                                        @RequestParam(name = "state", required = false) String state,
                                                        @RequestParam(name = "userNo", required = false) String userNo) {
        if(userNo!=null){
            CommonResponse commonResponse = CommonResponse.fail();
            commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
            return commonResponseResponseEntity;
        }

        String clientId = naverClientProperties.getClientId();
        String clientSecret = naverClientProperties.getClientSecret();

        Map<String, String> parms = getAccessTokenUsingCode(clientId, clientSecret, code, state);

        String accessToken = parms.get("access_token");
        UserInfo alreadyExistCheck = userService.findUserByUserAccessToken(accessToken);

        if(alreadyExistCheck!=null){
            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
            LocalDateTime seoulTime = LocalDateTime.now(seoulZoneId);
            LocalDateTime newExpiresTime = seoulTime.plusMinutes(50);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedStringExpiresIn = newExpiresTime.format(formatter);
            alreadyExistCheck.setExpiresIn(formattedStringExpiresIn);
            userService.updateAccessToken(alreadyExistCheck);
            CommonResponse commonResponse = CommonResponse.successOf(alreadyExistCheck);
            commonResponseResponseEntity = ResponseEntity.of(java.util.Optional.of(commonResponse));
            return commonResponseResponseEntity;
        }
        NaverUserInfoResponse userInfo = getNaverUserInfo(accessToken);

        if(userInfo!=null){
            CommonResponse commonResponse = handleUserRegistration(userInfo, parms);

            return ResponseEntity
                    .ok()
                    .body(commonResponse);
        }else{
            String errorMessage = "Required AccessCode again!";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.<String>error(ErrorResponse.of(errorMessage)));
        }

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
        userInfo.setAccessToken(accessToken);
        userInfo.setExpiresIn(userRegist.getExpiresIn());
        userInfo.setCreatedAt(userRegist.getCreatedAt());

        System.out.println("setCreatedAt : " + userRegist.getCreatedAt());
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

        ResponseEntity<NaverUserInfoResponse> response = null;
        
        try {
            response = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    entity,
                    NaverUserInfoResponse.class
            );
            
        } catch (HttpStatusCodeException e) {

            System.out.println("Error: Need new AccessCode");
            return null;
        }

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

        }

        if (dbUserEmail != null && dbUserEmail.equals(userEmail) && (dbAccessToken == null || !dbAccessToken.equals(newAccessToken)) ) {
            UserInfo updateUserToken = new UserInfo();

            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
            LocalDateTime seoulTime = LocalDateTime.now(seoulZoneId);
            LocalDateTime newExpiresTime = seoulTime.plusMinutes(50);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedStringExpiresIn = newExpiresTime.format(formatter);

            updateUserToken.setUserEmail(dbUserEmail);
            updateUserToken.setAccessToken(params.get("access_token"));
            updateUserToken.setRefreshToken(params.get("refresh_token"));
            updateUserToken.setExpiresIn(formattedStringExpiresIn);
            updateUserToken.setUserNo(dbUserNo);
            userService.updateAccessToken(updateUserToken);

        }
        UserInfo userExistCheck = userService.findUserByUserAccessToken(params.get("access_token"));

        if (userExistCheck != null) {
            String userNickname = userExistCheck.getUserNickname();

            if (userNickname != null) {
                UserInfo userCheck = userService.findUserByUserAccessToken(params.get("access_token"));

                userCheck.setExpiresIn(userCheck.getExpiresIn());
                userCheck.setRefreshToken(params.get("refresh_token"));

                CommonResponse commonResponse = CommonResponse.successOf(userCheck);

                return commonResponse;
            }

        }
        else {
            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
            LocalDateTime seoulTime = LocalDateTime.now(seoulZoneId);
            LocalDateTime newExpiresTime = seoulTime.plusMinutes(50);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedStringExpiresIn = newExpiresTime.format(formatter2);

            UserInfo userRegist = new UserInfo();
            userRegist.setUserEmail(userEmail);
            userRegist.setAccessToken(params.get("access_token"));
            userRegist.setExpiresIn(formattedStringExpiresIn);
            userRegist.setRefreshToken(params.get("refresh_token"));

            userService.beforeRegistUser(userRegist);

            UserInfo respUserInfo = userService.findUserByUserAccessToken(params.get("access_token"));
            CommonResponse commonResponse = CommonResponse.successOf(respUserInfo);
            return commonResponse;
        }

        return null;
    }

}
