package com.douzone.surveymanagement.surveytag.mapper;

import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 설문 태그에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyTagMapper {

    void insertSurveyTag(SurveyTagCreateDto surveyTagCreateDto);
}
