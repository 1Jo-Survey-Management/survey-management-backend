package com.douzone.surveymanagement.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * S3 Bucket 에서 객체 삭제 AOP의 PointCut 역할을 하는 애너테이션 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface S3DeleteObject {
}
