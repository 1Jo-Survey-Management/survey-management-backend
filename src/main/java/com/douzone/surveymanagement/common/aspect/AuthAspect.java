package com.douzone.surveymanagement.common.aspect;

import java.util.Objects;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 권한에 대한 처리 역할을 담당하는 Aspect 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Aspect
@Component
public class AuthAspect {


    @Pointcut("@annotation(com.douzone.surveymanagement.common.annotation.RequiredUser)")
    public void requiredUserPointcut() {
    }

    @Before("requiredUserPointcut()")
    public void checkRequiredAuth() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {

        }

        if (!authentication.isAuthenticated()) {

        }

    }
}
