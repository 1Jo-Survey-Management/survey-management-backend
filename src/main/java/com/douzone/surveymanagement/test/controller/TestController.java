package com.douzone.surveymanagement.test.controller;

import com.douzone.surveymanagement.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public ResponseEntity connectionTest() {

        int result = testService.testConnection();
        log.info("====================================");
        log.info("test Connection result : {}", result);
        log.info("====================================");

        return ResponseEntity.ok(result);
    }
}
