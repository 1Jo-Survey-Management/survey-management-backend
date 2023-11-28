package com.douzone.surveymanagement.user.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Schema(description = "사용자 인증 정보 및 개인 정보를 담고 있는 클래스")
public class CustomUserDetails implements UserDetails {
    @Schema(description = "사용자 번호")
    private final long userNo;
    @Schema(description = "사용자 이메일")
    private final String userEmail; // 수정: userEmail 추가
    @Schema(description = "사용자 닉네임")
    private final String userNickName;

    @Schema(description = "사용자 성별")
    private final String userGender;

    @Schema(description = "사용자 생년월일")
    private final Date userBirth;

    @Schema(description = "사용자 프로필 이미지 URL")
    private final String userImage;
    @Schema(description = "사용자의 권한 목록")
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userNo, String userEmail, String userNickName, String userGender, Date userBirth, String userImage, Collection<? extends GrantedAuthority> authorities) {
        this.userNo = Optional.ofNullable(userNo).orElse(0L);
        this.userEmail = Optional.ofNullable(userEmail).orElse("");
        this.userNickName = Optional.ofNullable(userNickName).orElse("");
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userImage = userImage;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부를 설정 (기본적으로 true)
    }

    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부를 설정 (기본적으로 true)
    }

    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 정보(비밀번호) 만료 여부를 설정 (기본적으로 true)
    }

    public boolean isEnabled() {
        return true; // 계정 활성화 여부를 설정 (기본적으로 true)
    }


}

