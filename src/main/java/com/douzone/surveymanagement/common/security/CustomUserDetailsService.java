package com.douzone.surveymanagement.common.security;
import com.douzone.surveymanagement.user.dto.UserInfo;
import com.douzone.surveymanagement.user.mapper.UserMapper;
import com.douzone.surveymanagement.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // MyBatis를 사용하여 사용자 정보를 데이터베이스에서 조회

        System.out.println("loadUserByUsername 입장 : " + username);

        UserInfo user = userService.findUserByUserAccessToken(username);

        System.out.println("유저들 : "+user.getAccessToken());


        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        UserDetails userDetails = User.withUsername(user.getAccessToken())
                .password("") // 사용자 패스워드 설정
                .authorities("ROLE_USER") // 사용자 권한 설정
                .accountExpired(false) // 계정 만료 여부 설정
                .build();

        System.out.println(userDetails.getUsername());

        return userDetails;
    }
}