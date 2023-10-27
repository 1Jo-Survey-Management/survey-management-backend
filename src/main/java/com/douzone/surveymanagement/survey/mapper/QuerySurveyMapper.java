package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 설문에 대한 조회를 담당하는 시그니쳐를 정의하는 인터페이스입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface QuerySurveyMapper {

    SurveyDetailsDto selectSurveyDetailsBySurveyNo(@Param("surveyNo") long surveyNo);

    String selectSurveyImageBySurveyNo(@Param("surveyNo") long surveyNo);
}
