package com.douzone.surveymanagement.user.service;
import com.douzone.surveymanagement.user.util.CustomAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = (String) authentication.getPrincipal();

        // 여기에서 accessToken을 검증하고 사용자 정보를 가져올 수 있어야 합니다.
        // 예를 들어, 사용자 정보를 데이터베이스에서 가져온다고 가정합니다.

        System.out.println("accessToken : " + accessToken);


        UserDetails userDetails = getUserDetailsFromAccessToken(accessToken);

        if (userDetails == null) {
            throw new BadCredentialsException("Access token is invalid or expired");
        }

        // 사용자가 인증되었으므로 인증된 Authentication 객체를 반환합니다.
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private UserDetails getUserDetailsFromAccessToken(String accessToken) {
        // 여기에서 accessToken을 사용하여 사용자 정보를 가져오는 로직을 구현해야 합니다.
        // 이 예시에서는 단순히 null을 반환하도록 하겠습니다.
        return null;
    }
}
