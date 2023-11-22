package com.douzone.surveymanagement.surveytag.mapper;

import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 설문 태그에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyTagMapper {

    /**
     * 설문을 저장하는 INSERT 메서드 입니다.
     *
     * @param surveyTagCreateDto 설문번호와 태그번호가 들어가 있는 Dto
     * @author : 강명관
     */
    void insertSurveyTag(SurveyTagCreateDto surveyTagCreateDto);


    /**
     * 설문 번호를 통해 설문의 모든 태그를 삭제하는 DELETE 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    void deleteAllSurveyTagBySurveyNo(@Param("surveyNo") long surveyNo);
}
