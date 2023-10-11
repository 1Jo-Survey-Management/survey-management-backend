package com.douzone.surveymanagement.common.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

/**
 * Multipart File 전송시 Content-Type: application/octet-stream 을 인식하지 못하는 문제를 해결하기 위한
 * Jackson2HttpMessageConvert 설정입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    /**
     * 생성자를 통해 ObjectMapper, MediaType을 받아옵니다.
     * MediaType 에 MediaType.APPLICATION_OCTET_STREAM을 추가하여
     * 메시지 컨버터가 이진 데이터 스트림을 처리할 수 있게 해줍니다.
     *
     * @param objectMapper Jackson2 ObjectMapper
     * @author 강명관
     */
    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

}
