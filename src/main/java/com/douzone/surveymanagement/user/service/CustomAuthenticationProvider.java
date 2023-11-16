package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import com.douzone.surveymanagement.user.util.GetAccessToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.douzone.surveymanagement.user.util.NTPTimeFetcher;

/**
 * 첫 로그인 회원 가입 시 인증 구분을 위한 CustomAuthenticationProvider 입니다
 * @author 김선규
 */
@Component
@Slf4j
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ClientRegistrationRepository clientRegistrationRepository;

    UserService userService;

    @ConfigurationProperties("spring.security.oauth2.client.registration.naver")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Authentication authenticationCheck = SecurityContextHolder.getContext().getAuthentication();

        if (authenticationCheck != null && authenticationCheck.isAuthenticated()) {
            System.out.println("(OAuth2Filter)이미 인증된 Authentication OAuth2Filter");
            return authenticationCheck;
        }
            // CustomAuthentication 객체가 아닌 객체가 접근시 접근 불가.
            if ((authentication instanceof CustomAuthenticationToken)) {
//------------------------- 토큰 갱신 api 요청 위한 정보들 모음 -------------------------------------------------
                ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("naver");
                String clientId = clientRegistration.getClientId();
                String clientSecret = clientRegistration.getClientSecret();

//                String clientId = "ukwEecKhMrJzOdjwpJfB";
//                String clientSecret = "au4WnhNLFn";
                CustomAuthenticationToken customToken = (CustomAuthenticationToken) authentication;
                String oldAccessToken = customToken.getCustomToken();

                log.debug("oldAccessToken : " + oldAccessToken);
                System.out.println("oldAccessToken : " + oldAccessToken);

                // 회원이 존재하는지 확인, 혹은 회원가입 중인 미완료 회원도 포함
                UserInfo userInfo = userService.findUserByUserAccessToken(oldAccessToken);
                System.out.println("Provider 회원 존재 여부 : " +userInfo);

                if(userInfo!=null){
                    System.out.println("Provider 회원 AccessToken : " + userInfo.getAccessToken());
                }

//-----------------------------------------------------------------------------------------------------------------
                // 토큰으로 유저가 검색 되면 적절한 AccessToken 을 가지고 있다는 인증이 됨
                // 따라서 이 안에서 두번째 유효성 검사인 유효 기간 만료 체크만 되면 인증 되고 authentication 객체 넘기면 됨
                // 유효 기간 만료시 토큰 갱신 해주고 객체 넘기면 됨
                if (userInfo != null) {
                    String refreshToken = userInfo.getRefreshToken();
                    String expiresCheck = userInfo.getExpiresIn();

                    NTPTimeFetcher ntpTimeFetcher = new NTPTimeFetcher();
                    DateTimeFormatter currentTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    ZonedDateTime koreaTime = ZonedDateTime.parse(ntpTimeFetcher.getFormattedKoreaTime());
                    String currentSeoulTimeFormatted = koreaTime.format(currentTimeFormatter);

                    log.debug("현재시간 : " + currentSeoulTimeFormatted);
                    log.debug("유효시간 : " + expiresCheck);
                    System.out.println("현재시간 : " + currentSeoulTimeFormatted);
                    System.out.println("유효시간 : " + expiresCheck);

                    // 기본적으로 회원가입을 위한 첫 로직을 지나친 회원은 accessToken과 expiresIn을 무조건 가지고 있다.
                    // 이것이 null이거나 존재하지 않는다는 event는 존재하지 않는다.
                    if (expiresCheck == null || expiresCheck.equals(" ")) {
                        log.error("유효시간이 존재하지 않음");
                        return null;
                    }

                    // 1. 적절한 토큰 인증은 됐으나 유효 기간 만료가 됐을 시 갱신을 해줘야 하기 때문에 체크 하는 로직
                    // 유효시간이 db에 존재하지만 유효시간을 넘겼을때 갱신하는 로직
                    // 현재 시간과 db의 유효시간을 비교한다. (현재시간은 NTP서버의 시간을 통해서 무결성을 유지한다)
                    String formattedExpiresCheck = expiresCheck.replace(" ", "T") + "+09:00";
                    ZonedDateTime expiresTime = ZonedDateTime.parse(formattedExpiresCheck);

                    System.out.println("유효시간 비교 현재시각: " + koreaTime );
                    System.out.println("유효시간 비교 유효시간: " + expiresTime);

                    // 유효 시간을 넘었기 때문에 토큰 갱신
                    if (koreaTime.isAfter(expiresTime)) {
                        System.out.println("유효시간 넘어서 토큰 자동 갱신");


                        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=" + clientId +
                                "&client_secret=" + clientSecret + "&refresh_token=" + refreshToken;

                        Map<String, String> params = GetAccessToken.getToken(tokenUrl);

                        String accessToken = params.get("access_token");
                        String renewRefreshToken = params.get("refresh_token");

                        // 토큰 발급 받은 만료시간 설정 (버퍼 시간 고려 50분으로 설정)
                        ZonedDateTime newExpiresTime = koreaTime.plusMinutes(50);

                        // yyyy-MM-dd HH:mm:ss 형식으로 ZonedDateTime를 문자열로 변환
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
                        // 토큰 체크도 했고 유효시간도 넘지 않았으므로 정상 인증 진행하면 된다.
                        // 미완료 회원도 db에 토큰과 유효기간이 존재하므로 여기서 인증시키면 된다.

                        // 1. db에 닉네임이 들어가있으면 정상적으로 가입된 회원으로 간주한다.
                        if (userInfo.getUserNickname() != null) {
                            UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);

                            System.out.println("회원 인증 완료 provider 지나감");

                            CustomAuthentication customAuthentication = new CustomAuthentication(
                                    new CustomUserDetails(user.getUserNo(), user.getUserEmail(), user.getUserNickname(), user.getUserGender(), user.getUserBirth(), user.getUserImage(), customToken.getAuthorities()),
                                    oldAccessToken
                            );
                            return customAuthentication;

                        }
                        // 2. db에 닉네임이 없으면 회원가입이 아직 완료되지 않은 회원이라 비어있는 인가 객체 반환
                        else {
                            UserInfo user = userService.findUserByUserAccessToken(oldAccessToken);

                            log.debug("토큰으로 회원 확인 : " + user);
                            System.out.println("미완료 회원 회원가입 중 provider 지나감");

                            CustomAuthentication customAuthentication = new CustomAuthentication(

                                    new CustomUserDetails(user.getUserNo(), null, null, null, null, null, customToken.getAuthorities()),
                                    oldAccessToken
                            );
                            return customAuthentication;
                        }

                    }
                }
                else {

                    // 1. accessToken이 없는 이유 중 가장 처음 회원가입으로 접근 하는 경우가 있다. 이럴땐 url 체크해서 임시 인가 해준다
                    if (((CustomAuthenticationToken) authentication).getCallBackUri().equals("/oauthLogin/oauth2/code/naver")) {
                        log.debug("CallBack Authentication");
                        System.out.println("첫 회원가입 provider 지나감");

                        CustomAuthentication customAuthentication = new CustomAuthentication(
                                new CustomUserDetails(null, null, null, null, null, null, customToken.getAuthorities()),
                                null
                        );

                        return customAuthentication;
                    }

                    //2. 메인의 카드들은 비회원도 접근 가능하다.
                    if(((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/weekly")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/recent")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/closing")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/api/surveys/surveyAll")){

                        System.out.println("(Provider)weekly 비회원 데이터");
                        
                        CustomAuthentication customAuthentication = new CustomAuthentication(
                                new CustomUserDetails(null, null, null, null, null, null, customToken.getAuthorities()),
                                null
                        );

                        return customAuthentication;
                    }

                    // 2. 회원 가입 url의 접근 외에는 부적절한 접근으로 재로그인 시킨다.
                    log.error("부적절한 접근");
                    return null;
                }
            }




        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
