package com.douzone.surveymanagement.user.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final long userNo;
    private final String userEmail; // 수정: userEmail 추가
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(long userNo, String userEmail, String nickname, Collection<? extends GrantedAuthority> authorities) {
        this.userNo = userNo;
        this.userEmail = userEmail; // 수정: userEmail 설정
        this.nickname = nickname;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호 필드가 없는 경우, 보통은 null을 반환합니다.
    }

    @Override
    public String getUsername() {
        return null;
    }

    public long getUserNo() {
        return userNo;
    }

    public String getEmail() {
        return userEmail; // 수정: userEmail 반환
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부를 설정 (기본적으로 true)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부를 설정 (기본적으로 true)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 정보(비밀번호) 만료 여부를 설정 (기본적으로 true)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부를 설정 (기본적으로 true)
    }
}


