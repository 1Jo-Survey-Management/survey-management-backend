package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 각 페이지에 필요한 설문을 데이터베이스에서 가져오는 마이바티스 Mapper입니다.
 *
 * @author : 엄아진
 * @since : 1.0
 *
 *
 */
@Mapper
public interface QuerySurveyMapper {
    List<SurveyDetailInfoDto> selectWeeklySurvey();

    List<SurveyDetailInfoDto> selectRecentSurvey();

    List<SurveyDetailInfoDto> closingSurvey();
    List<SurveyDetailInfoDto> selectAllSurvey(int nextPage);

    List<SurveyDetailInfoDto> selectClosingSurvey(int nextPage);
    List<SurveyDetailInfoDto> selectPostSurvey(int nextPage);

}
