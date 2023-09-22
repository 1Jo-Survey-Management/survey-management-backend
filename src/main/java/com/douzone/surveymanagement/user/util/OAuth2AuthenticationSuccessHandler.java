package com.douzone.surveymanagement.user.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    // SuccessHandeler 에서는 accesstoken으로 프로필 인증한 이후 들고온 authentication 객체에 담겨있는 유저 정보들로 처리를 하는 메서드이다.
    // accesstoken은 CustomOAuth2UserService에서 OAuth2UserRequest 에 담겨 있어서 메서드로 빼서 쓸수 있다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        System.out.println(authentication);

        Authentication check = SecurityContextHolder.getContext().getAuthentication();
        if (check != null && check.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) check.getPrincipal();

            // Access Token 가져오기
            String accessToken = oauth2User.getAttribute("access_token");

            System.out.println("oauth2User : " + oauth2User);

            // Access Token을 사용하여 필요한 작업 수행
            // ...
        }

    }
}
