package com.douzone.surveymanagement.mysurvey.mapper;

import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 내가 작성/참여한 설문 목록 매퍼 인터페이스입니다.
 */
@Mapper
public interface MySurveyMapper {
    List<MySurveyDTO> selectMySurveysWithSorting(long userNo);

    List<MySurveyDTO> selectMyParticipatedSurveys(long userNo);

    int updateMySurveysInProgress(MySurveyDTO mySurveyDTO);
}
