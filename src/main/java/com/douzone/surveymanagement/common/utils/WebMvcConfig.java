package com.douzone.surveymanagement.common.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Web MVC 설정 클래스입니다.
 *
 * <p>이 클래스는 CORS 설정을 구성합니다.</p>
 *
 * @author 박창우
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * CORS(Cross-Origin Resource Sharing) 설정을 추가합니다.
     *
     * @param registry CORS 레지스트리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:3000");
    }
}
