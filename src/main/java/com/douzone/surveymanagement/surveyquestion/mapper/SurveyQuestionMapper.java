package com.douzone.surveymanagement.surveyquestion.mapper;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 설문 문항에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyQuestionMapper {

    /**
     * 설문의 문항을 생성하는 쿼리 입니다.
     *
     * @param surveyQuestionCreateDto 설문 문항에 대한 정보
     * @return 설문 문항이 등록되고 난 후의 PK
     * @author : 강명관
     */
    long insertSurveyQuestion(SurveyQuestionCreateDto surveyQuestionCreateDto);

    /**
     * 설문 번호를 통해 해당 설문에 대한 문항들을 삭제하는 쿼리 입니다.
     *
     * @param surveyNo 설문번호
     * @author : 강명관
     */
    void deleteAllSurveyQuestionsBySurveyNo(@Param("surveyNo") long surveyNo);

}
