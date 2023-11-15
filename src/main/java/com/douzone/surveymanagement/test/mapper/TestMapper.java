package com.douzone.surveymanagement.test.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Mapper
public interface TestMapper {

    int testConnection();
}
