package com.douzone.surveymanagement.common.aspect;

import com.douzone.surveymanagement.common.utils.S3ObjectDeleter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * S3 이미지를 삭제하는 AOP를 담당하는 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Slf4j
@Aspect
@Component
public class S3Aspect {

    private final S3ObjectDeleter s3ObjectDeleter;

    public S3Aspect(S3ObjectDeleter s3ObjectDeleter) {
        this.s3ObjectDeleter = s3ObjectDeleter;
    }

    private static final String S3_DELETE_OBJECT_HEADER = "X-Previous-Image-URL";

    @Pointcut("@annotation(com.douzone.surveymanagement.common.annotation.S3DeleteObject)")
    public void s3DeleteObjectPointCut() {
    }

    @After("s3DeleteObjectPointCut()")
    public void afterS3DeleteObject() {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String targetObjectUrl = request.getHeader(S3_DELETE_OBJECT_HEADER);

        if (Objects.nonNull(targetObjectUrl) && !targetObjectUrl.isEmpty()) {
            s3ObjectDeleter.deleteObjectByObjectUrl(targetObjectUrl);
            log.debug("Delete S3 Object {}", targetObjectUrl);
        }
    }

}
