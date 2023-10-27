package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 설문에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface CommandSurveyMapper {

    /**
     * 설문에 대한 정보를 등록하는 쿼리 입니다.
     *
     * @param surveyInfoCreateDto 설문 정보를 담은 Dto
     * @author : 강명관
     */
    void insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto);

    /**
     * 설문의 마감일이 오늘보다 이전일 경우 마감상태로 변경하는 쿼리 입니다.
     *
     * @author : 강명관
     */
    void updateSurveyStatusToDeadline();

    /**
     * 설문에 대한 정보를 수정하는 쿼리 입니다.
     *
     * @param surveyUpdateDto 설문 정보 수정에 필요한 데이터를 담은 Dto
     * @author : 강명관
     */
    void updateSurvey(SurveyInfoUpdateDto surveyUpdateDto);


    /**
     * 설문의 번호를 통해 설문 상태를 작성 -> 게시 상태로 변경하는 쿼리 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    int updateSurveyStatusToPostFromInProgress(@Param("surveyNo") long surveyNo);
}
