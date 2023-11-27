package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.dto.NaverClientProperties;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
/**
 * 첫 로그인 회원 가입 시 인증 구분을 위한 CustomAuthenticationProvider 입니다
 * @author 김선규
 */
@Component
@Slf4j
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final NaverClientProperties naverClientProperties;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationCheck != null && authenticationCheck.isAuthenticated()) {
            return authenticationCheck;
        }
            if ((authentication instanceof CustomAuthenticationToken)) {
                String clientId = naverClientProperties.getClientId();
                String clientSecret = naverClientProperties.getClientSecret();
                CustomAuthenticationToken customToken = (CustomAuthenticationToken) authentication;
                String oldAccessToken = customToken.getCustomToken();
                UserInfo userInfo = userService.findUserByUserAccessToken(oldAccessToken);
            if (userInfo != null) {
                    String refreshToken = userInfo.getRefreshToken();
                    String expiresCheck = userInfo.getExpiresIn();
                    String userNickname = userInfo.getUserNickname();

                    ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
                    LocalDateTime seoulTime = LocalDateTime.now(seoulZoneId);

                    String formattedExpiresCheck = expiresCheck.replace(" ", "T") + "+09:00";
                    LocalDateTime expiresTime = LocalDateTime.parse(formattedExpiresCheck, DateTimeFormatter.ISO_DATE_TIME);

                    if (seoulTime.isAfter(expiresTime)) {
                        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=" + clientId +
                                "&client_secret=" + clientSecret + "&refresh_token=" + refreshToken;

                        Map<String, String> params = GetAccessToken.getToken(tokenUrl);

                        String accessToken = params.get("access_token");
                        String renewRefreshToken = params.get("refresh_token");

                        LocalDateTime newExpiresTime = seoulTime.plusMinutes(50);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedStringExpiresIn = newExpiresTime.format(formatter);
                        UserInfo refreshTokenUserInfo;
                        refreshTokenUserInfo = userService.findUserByUserAccessToken(oldAccessToken);
                        refreshTokenUserInfo.setAccessToken(accessToken);
                        refreshTokenUserInfo.setRefreshToken(renewRefreshToken);
                        refreshTokenUserInfo.setExpiresIn(formattedStringExpiresIn);
                        userService.updateAccessToken(refreshTokenUserInfo);
                        CustomAuthentication customAuthentication = new CustomAuthentication(
                                new CustomUserDetails(refreshTokenUserInfo.getUserNo(), refreshTokenUserInfo.getUserEmail(), refreshTokenUserInfo.getUserNickname(), refreshTokenUserInfo.getUserGender(), refreshTokenUserInfo.getUserBirth(), refreshTokenUserInfo.getUserImage(), customToken.getAuthorities()),
                                accessToken
                        );
                        return customAuthentication;
                    } else {
                        if (userNickname != null) {
                            UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);
                            CustomAuthentication customAuthentication = new CustomAuthentication(
                                    new CustomUserDetails(user.getUserNo(), user.getUserEmail(), user.getUserNickname(), user.getUserGender(), user.getUserBirth(), user.getUserImage(), customToken.getAuthorities()),
                                    oldAccessToken
                            );
                            return customAuthentication;
                        }
                        else {
                            UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);
                            CustomAuthentication customAuthentication = new CustomAuthentication(
                                    new CustomUserDetails(user.getUserNo(), null, null, null, null, null, customToken.getAuthorities()),
                                    oldAccessToken
                            );
                            return customAuthentication;
                        }
                    }
                }
                else {
                    if (((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/oauthLogin/oauth2/code/naver")
                            || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/oauthLogin/check-duplicate-nickname")) {
                        CustomAuthentication customAuthentication = new CustomAuthentication(
                                new CustomUserDetails(null, null, null, null, null, null, customToken.getAuthorities()),
                                null
                        );
                        return customAuthentication;
                    }
                    if(((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/weekly")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/recent")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/closing")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/surveyall")
                       || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/search")

                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/survey/resultall/nonMember")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/select-closing")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/select-post")){

                        CustomAuthentication customAuthentication = new CustomAuthentication(
                                new CustomUserDetails(null, null, null, null, null, null, customToken.getAuthorities()),
                                null
                        );
                        return customAuthentication;
                    }
                    log.error("AccessToken이 존재하지 않고 회원가입/비회원 접근도 아닌 부적절한 접근입니다!");
                    return null;
                }
            }
        log.error("CustomAuthentication이 아닌 Authentication이 들어왔습니다.");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
