package com.douzone.surveymanagement.common.aspect;

import com.douzone.surveymanagement.common.exception.BadRequestException;
import com.douzone.surveymanagement.user.util.CustomAuthentication;
import java.util.Arrays;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
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
@Slf4j
@Aspect
@Component
public class AuthAspect {

    private static final String AUTH_EXCEPTION_MESSAGE = "로그인되지 않은 사용자 입니다.";

    /**
     * 인가에 대한 AOP의 PointCut을 지정하는 메서드 입니다.
     *
     * @author : 강명관
     */
    @Pointcut("@annotation(com.douzone.surveymanagement.common.annotation.RequiredUser)")
    public void requiredUserPointcut() {
    }

    /**
     * PointCut에 대해서 인가 받은 사용자인지에 대한 검증을 하는 AOP 메서드 입니다.
     *
     * @param joinPoint joinPoint
     * @author : 강명관
     */
    @Before("requiredUserPointcut()")
    public void checkRequiredAuth(JoinPoint joinPoint) {

        logMethodDetails(joinPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isAuthenticationTokenEmpty(authentication)) {
            throw new BadRequestException(AUTH_EXCEPTION_MESSAGE);
        }

        if (!(authentication instanceof CustomAuthentication)) {
            throw new BadRequestException(AUTH_EXCEPTION_MESSAGE);
        }

        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
        if (isInvalidCustomAuthentication(customAuthentication)) {
            throw new BadRequestException(AUTH_EXCEPTION_MESSAGE);
        }

    }

    /**
     * 해당 포인트컷으로 지정된 메서드에 대해 로깅하기 위한 메서드 입니다.
     *
     * @param joinPoint joinPoint
     * @author : 강명관
     */
    private void logMethodDetails(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.debug("Method Name : {}", methodName);
        log.debug("Arguments: " + Arrays.toString(methodArgs));
    }

    /**
     * Security Context Holder 에 정합한 Authentication 객체가 존재하는지 검사하는 메서드 입니다.
     *
     * @param authentication Authentication Token
     * @return Security Context Holder가 비어있거나 인가되지 않았을 경우 ture
     * @author : 강명관
     */
    private boolean isAuthenticationTokenEmpty(Authentication authentication) {
        return Objects.isNull(authentication) || !authentication.isAuthenticated();
    }

    /**
     * CustomAuthentication 토큰이 유효한지 검사하는 메서드 입니다.
     *
     * @param customAuthentication Authentication 을 상속받고 있는 Custom Authentication Token
     * @return 비정상적인 Authentication Token일 경우 true를 반환합니다.
     * @author : 강명관
     */
    private boolean isInvalidCustomAuthentication(CustomAuthentication customAuthentication) {
        return Objects.isNull(customAuthentication.getCustomToken()) ||
            customAuthentication.getCustomToken().isEmpty();
    }

}
