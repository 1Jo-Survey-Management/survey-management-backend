//package com.douzone.surveymanagement.user.util;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.Optional;
//
//@AllArgsConstructor
//@Getter
//public class CustomUserDetails implements UserDetails {
//    private final long userNo;
//    private final String userEmail; // 수정: userEmail 추가
//    private final String nickname;
//    private final String userGender;
//    private final Date userBirth;
//    private final String userImage;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserDetails(Long userNo, String userEmail, String nickname, String userGender, Date userBirth, String userImage, Collection<? extends GrantedAuthority> authorities) {
//        this.userNo = Optional.ofNullable(userNo).orElse(0L);
//        this.userEmail = Optional.ofNullable(userEmail).orElse("");
//        this.nickname = Optional.ofNullable(nickname).orElse("");
//        this.userGender = userGender;
//        this.userBirth = userBirth;
//        this.userImage = userImage;
//        this.authorities = authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    public boolean isAccountNonExpired() {
//        return true; // 계정 만료 여부를 설정 (기본적으로 true)
//    }
//
//    public boolean isAccountNonLocked() {
//        return true; // 계정 잠금 여부를 설정 (기본적으로 true)
//    }
//
//    public boolean isCredentialsNonExpired() {
//        return true; // 자격 증명 정보(비밀번호) 만료 여부를 설정 (기본적으로 true)
//    }
//
//    public boolean isEnabled() {
//        return true; // 계정 활성화 여부를 설정 (기본적으로 true)
//    }
//}
//
