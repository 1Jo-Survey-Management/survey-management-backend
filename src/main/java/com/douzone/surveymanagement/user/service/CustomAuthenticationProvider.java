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

        System.out.println("authenticate 들어옴, 토큰값 : " + tokenValue);

        // 토큰을 검증하고 사용자를 인증하는 사용자 지정 로직을 여기에 추가합니다.
        // 예를 들어, 토큰이 유효한지 확인하고 해당 토큰에 연결된 사용자를 검색할 수 있습니다.

        // 들어온 토큰으로 회원이 존재하면 회원 정보들을 들고 와서 CustomAuthentication 객체를 생성하여 반환
        UserInfo user = userService.findUserByUserAccessToken(tokenValue);



        if (user != null) {

            System.out.println("토큰으로 인한 회원 존재 : " + user);
            // 사용자가 인증되면 CustomAuthentication 객체를 생성하고 사용자 정보를 설정
            CustomAuthentication customAuthentication = new CustomAuthentication(
                    //아래의 형식으로 다 넣어주기
                    new CustomUserDetails(user.getUserNo(), user.getUserEmail(), user.getUserNickname(), customToken.getAuthorities()),
                    tokenValue
            );

            return customAuthentication;
        }

        System.out.println("인증 실패, 혹은 새로운 회원");
        return null; // 인증 실패 예제
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
