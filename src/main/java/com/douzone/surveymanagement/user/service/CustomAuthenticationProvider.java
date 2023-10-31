package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * 첫 로그인 회원 가입 시 인증 구분을 위한 CustomAuthenticationProvider 입니다
 * @author 김선규
 */
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    private final ClientRegistrationRepository clientRegistrationRepository;

    public CustomAuthenticationProvider(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof CustomAuthenticationToken)) {
            return null;
        }
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();
        CustomAuthenticationToken customToken = (CustomAuthenticationToken) authentication;
        String oldAccessToken = customToken.getCustomToken();

        log.debug("oldAccessToken : " + oldAccessToken);

        // db에 있는 유효시간이 유효한지 확인 후, 유효하지 않으면 갱신 시켜줌
        UserInfo userInfo = userService.findUserByUserAccessToken(oldAccessToken);

        // accessToken 없으면 임시 객체이기 때문에 토큰 유효성 검사 생략
        if (userInfo != null) {
            String refreshToken = userInfo.getRefreshToken();
            String expiresCheck = userInfo.getExpiresIn();

            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
            ZonedDateTime currentSeoulTime = ZonedDateTime.now(seoulZoneId);

            log.debug("현재시간 : " + currentSeoulTime);
            log.debug("유효시간 : " + expiresCheck);

            ZonedDateTime parsedInstant = null;
            if (expiresCheck != null) {
                expiresCheck = expiresCheck.replace(' ', 'T');
//                expiresCheck = expiresCheck + "+09:00";
                parsedInstant = ZonedDateTime.parse(expiresCheck);
                log.debug("유효시간 : " + parsedInstant);
            } else {
                log.debug("토큰 유효시간 만료");
                String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=" + clientId +
                        "&client_secret=" + clientSecret + "&refresh_token=" + refreshToken;

                Map<String, String> params = GetAccessToken.getToken(tokenUrl);

                String accessToken = params.get("access_token");
                String renewRefreshToken = params.get("refresh_token");

                // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
                ZonedDateTime newExpiresTime = currentSeoulTime.plusMinutes(50);

                // yyyy-MM-dd HH:mm:ss 형식으로 ZonedDateTime를 문자열로 변환
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedStringExpiresIn = newExpiresTime.format(formatter2);

                UserInfo userInfo2;

                userInfo2 = userService.findUserByUserAccessToken(oldAccessToken);
                userInfo2.setAccessToken(accessToken);
                userInfo2.setRefreshToken(renewRefreshToken);
                userInfo2.setExpiresIn(formattedStringExpiresIn);

                oldAccessToken = accessToken;

                userService.updateAccessToken(userInfo2);
            }

            if (parsedInstant != null) {
                int comparisonResult = currentSeoulTime.compareTo(parsedInstant);

                // 유효시간이 같지 않으면 갱신
                if (comparisonResult > 0) {
                    String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=" + clientId +
                            "&client_secret=" + clientSecret + "&refresh_token=" + refreshToken;
                    
                    Map<String, String> params = GetAccessToken.getToken(tokenUrl);

                    String accessToken = params.get("access_token");
                    String renewRefreshToken = params.get("refresh_token");

                    // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
                    ZonedDateTime newExpiresTime = currentSeoulTime.plusMinutes(50);

                    // yyyy-MM-dd HH:mm:ss 형식으로 ZonedDateTime를 문자열로 변환
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedStringExpiresIn = newExpiresTime.format(formatter2);

                    UserInfo userInfo2;

                    userInfo2 = userService.findUserByUserAccessToken(oldAccessToken);
                    userInfo2.setAccessToken(accessToken);
                    userInfo2.setRefreshToken(renewRefreshToken);
                    userInfo2.setExpiresIn(formattedStringExpiresIn);

                    oldAccessToken = accessToken;

                    userService.updateAccessToken(userInfo2);

                }
            }
        }
        // 브라우저 상의 오류로 인해 accessToken이 LocalStorage에 저장되지 않아 db에는 갱신되고 넘어오는 accessToken이 다를때
        else{

        }

            // 1. 회원 확인
            if (!oldAccessToken.equals("")) {
                UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);

                if (user != null) {
                    CustomAuthentication customAuthentication = new CustomAuthentication(
                            new CustomUserDetails(user.getUserNo(), user.getUserEmail(), user.getUserNickname(), user.getUserGender(), user.getUserBirth(), user.getUserImage(), customToken.getAuthorities()),
                            oldAccessToken
                    );
                    return customAuthentication;
                }

                return null;
            }
            // 2. callback uri로 인하여 임시적인 인가 객체를 반환해야 될 수 있음
            else if (((CustomAuthenticationToken) authentication).getCallBackUri().equals("/login/oauth2/code/naver")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/login/oauth2/code/naver/call")) {
                log.debug("CallBack Authentication");

                CustomAuthentication customAuthentication = new CustomAuthentication(
                        new CustomUserDetails(null, null, null, null, null, null, customToken.getAuthorities()),
                        null
                );

                return customAuthentication;
            }
            // 3. 토큰만 들어오면 회원가입이 아직 완료되지 않은 회원이라 비어있는 인가 객체 반환
            else {
                UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);

                log.debug("토큰으로 회원 확인 : " + user);

                if (user != null) {
                    CustomAuthentication customAuthentication = new CustomAuthentication(

                            new CustomUserDetails(user.getUserNo(), null, null, null, null, null, customToken.getAuthorities()),
                            oldAccessToken
                    );
                    return customAuthentication;
                }

                return null;
            }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
