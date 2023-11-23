package com.douzone.surveymanagement.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * S3의 Object를 삭제하는 로직을 담당하는 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Slf4j
@Component
public class S3ObjectDeleter {

    private final AmazonS3Client amazonS3Client;

    @Value("${s3.bucket.name}")
    private String bucketName;

    public S3ObjectDeleter(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    /**
     * object 의 URL 을 통해서 해당 S3 객체를 삭제하는 메서드 입니다.
     *
     * @param objectUrl S3 Object의 URL
     * @author : 강명관
     */
    public void deleteObjectByObjectUrl(String objectUrl) {
        String[] parts = objectUrl.split("/");
        String objectKeyBeforeUrlDecode =
            String.join("/", Arrays.copyOfRange(parts, 3, parts.length));

        String urlDecodedObjectKey = getUrlDecodedObjectKey(objectKeyBeforeUrlDecode);
        deleteObject(urlDecodedObjectKey);
    }

    /**
     * Object Key를 통해서 S3의 버킷에 존재하는 객체를 삭제하는 메서드 입니다.
     *
     * @param objectKey 삭제할 Object의 Key
     * @author : 강명관
     */
    private void deleteObject(String objectKey) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
        } catch (Exception e) {
            log.error("S3 Delete Object Error {}", e.toString());
        }
    }

    /**
     * 디코딩이 되지 않은 S3 Object URL 을 통해서 얻어온 Object Key 값을 Decode 하는 메서드 입니다.
     *
     * @param objectKeyBeforeDecoded 디코딩이 되지 않은 S3 Object URL 을 통해서 얻어온 Object Key
     * @return 디코딩 된 Object Key
     * @author : 강명관
     */
    private String getUrlDecodedObjectKey(String objectKeyBeforeDecoded) {
        return URLDecoder.decode(objectKeyBeforeDecoded, StandardCharsets.UTF_8);
    }
}
