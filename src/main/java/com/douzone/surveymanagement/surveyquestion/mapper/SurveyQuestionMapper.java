package com.douzone.surveymanagement.surveyquestion.mapper;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 설문 문항에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyQuestionMapper {

    void insertSurveyQuestion(SurveyQuestionCreateDto surveyQuestionCreateDto);
}
