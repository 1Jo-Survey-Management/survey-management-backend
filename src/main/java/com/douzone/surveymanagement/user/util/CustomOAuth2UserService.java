package com.douzone.surveymanagement.user.util;

import com.douzone.surveymanagement.user.dto.OAuth2UserInfo;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.AuthProvider;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserServiceImpl userService;

    //
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        System.out.println("액세스 토큰토큰 : "+oAuth2UserRequest.getAccessToken().getTokenValue());

        System.out.println(oAuth2User.getName());




//        return processOAuth2User(oAuth2UserRequest, oAuth2User);
        return null;
    }

//
//    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//
//        OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) oAuth2User.getAttributes();
//
//        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
//            throw new RuntimeException("Email not found from OAuth2 provider");
//        }
//
//
//        //TODO : userRepository 는 dao이다 userService의 메서드를 사용해서 사용자 인증 부분 바꾸기
//        //아래의 코드는 email로 인증하고 사용자 정보 다 가져 오는듯 하다 고치기
//        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
//        //이미 가입된 경우
//        if (user != null) {
//            if (!user.getAuthProvider().equals(authProvider)) {
//                throw new RuntimeException("Email already signed up.");
//            }
//            user = updateUser(user, oAuth2UserInfo);
//        }
//        //가입되지 않은 경우
//        else {
//            user = registerUser(authProvider, oAuth2UserInfo);
//        }
//        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
//    }
//
//    //TODO : User의 클래스는 이미 spring security로 정의 되어있다. email 등을 쓰려면 수정이 필요한데 가능한지 확인하기
//    private User registerUser(AuthProvider authProvider, OAuth2UserInfo oAuth2UserInfo) {
//        User user = User.builder()
//                .email(oAuth2UserInfo.getEmail())
//                .name(oAuth2UserInfo.getName())
//                .oauth2Id(oAuth2UserInfo.getOAuth2Id())
//                .authProvider(authProvider)
//                .role(Role.ROLE_USER)
//                .build();
//
//        return userRepository.save(user);
//    }
//
//    //TODO : 이 부분도 마찬가지로 회원가입 완료되는 부분, userRepository 말고 userService로 수정하기
//    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
//        return userRepository.save(user.update(oAuth2UserInfo));
//    }
}