package com.douzone.surveymanagement.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AWS S3를 이용하기 위한 Configuration 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Slf4j
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * AWS IAM 사용자 등록을 위한 Bean 입니다.
     *
     * @return BasicAWSCredentials
     * @author : 강명관
     */
    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    /**
     * IAM을 통해 S3 에 접근하기 위한 Bean 입니다.
     *
     * @return AmazonS3Client
     * @author : 강명관
     */
    @Bean
    public AmazonS3Client amazonS3Client() {

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
            .build();
    }
}
