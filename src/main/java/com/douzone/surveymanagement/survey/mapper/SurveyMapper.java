package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.request.SurveyCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 설문에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyMapper {

    void insertSurvey(SurveyCreateDto surveyCreateDto);
}
