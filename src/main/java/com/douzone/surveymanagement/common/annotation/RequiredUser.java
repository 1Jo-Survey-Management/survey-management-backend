package com.douzone.surveymanagement.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 유저 권한이 필요한 AOP PointCut 역할을 담당할 Annotation 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredUser {
}
