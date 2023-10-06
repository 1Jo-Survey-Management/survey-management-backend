package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import com.douzone.surveymanagement.user.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof CustomAuthenticationToken)) {
            return null;
        }

        CustomAuthenticationToken customToken = (CustomAuthenticationToken) authentication;
        String tokenValue = customToken.getCustomToken();
        Long userNo = customToken.getUserNo();

        System.out.println("authenticate 들어옴, 토큰값 : " + tokenValue);
        System.out.println("userNo : " + userNo);


        // TODO: 토큰이랑 회원번호가 들어오면 회원이 있다는 말이니 1번으로 가고
        // TODO: 토큰만 들어오면 회원이 없다는 말이라서 2번으로 간다


        // 1. 토큰과 회원번호가 들어오면 회원 확인, mysql에서 회원번호 1부터 시작이기 때문에 0이면 회원일 수 없음
        if(!tokenValue.equals("") && userNo!=0 ){
            UserInfo user = userService.findUserByAccessTokenAndUserNo(tokenValue, userNo);

            System.out.println("토큰과 회원 번호로 회원 확인 : " + user);

            // 토큰과 회원번호를 아무렇게나 적어서 인가 됨을 방지
            if(user!=null) {
                // 사용자가 인증되면 CustomAuthentication 객체를 생성하고 사용자 정보를 설정
                CustomAuthentication customAuthentication = new CustomAuthentication(
                        //아래의 형식으로 다 넣어주기
                        new CustomUserDetails(user.getUserNo(), user.getUserEmail(), user.getUserNickname(), customToken.getAuthorities()),
                        tokenValue
                );
                return customAuthentication;
            }

            return null;
        }
        // 2. 만약 callback uri로 인하여 임시적인 인가 객체를 반환해야 될 수 있음
        else if (((CustomAuthenticationToken) authentication).getCallBackUri().equals("/login/oauth2/code/naver")
                    || ((CustomAuthenticationToken) authentication).getCallBackUri().equals("/login/oauth2/code/naver/call")) {

            System.out.println("콜백 인가 들어옴");
            CustomAuthentication customAuthentication = new CustomAuthentication(
                    // 인가만 해줌
                    new CustomUserDetails(null, null, null, customToken.getAuthorities()),
                    null
            );

            System.out.println("customAuthentication : " + customAuthentication.getAuthorities());
            return customAuthentication;
        }
        // 3. 토큰만 들어오면 회원가입이 아직 완료되지 않은 회원이라 비어있는 인가 객체 반환
        else {
            UserInfo user = userService.findUserByUserAccessToken(tokenValue);

            System.out.println("토큰으로 회원 확인 : " + user);

            // 토큰이 유효하지 않아 찾을 수 없으면 null 반환, 회원가입 단계라 토큰이 유효 하지 않을 수 없음
            // 즉, 보안 공격일 가능성
            if(user!=null) {
                CustomAuthentication customAuthentication = new CustomAuthentication(
                        //아래의 형식으로 다 넣어주기
                        new CustomUserDetails(user.getUserNo(), null, null, customToken.getAuthorities()),
                        tokenValue
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
