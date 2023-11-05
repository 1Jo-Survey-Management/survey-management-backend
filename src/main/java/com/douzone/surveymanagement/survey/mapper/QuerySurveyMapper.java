package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

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

    List<SurveyDetailInfoDto> selectWeeklySurvey();

    List<SurveyDetailInfoDto> selectRecentSurvey();

    List<SurveyDetailInfoDto> closingSurvey();

    List<SurveyDetailInfoDto> selectAllSurvey(int nextPage);

    List<SurveyDetailInfoDto> selectClosingSurvey(int nextPage);

    List<SurveyDetailInfoDto> selectPostSurvey(int nextPage);

}
