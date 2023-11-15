package com.douzone.surveymanagement.test.service;

import com.douzone.surveymanagement.test.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {


    private final TestMapper testMapper;

    @Override
    public int testConnection() {
        return testMapper.testConnection();
    }
}
