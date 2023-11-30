package com.douzone.surveymanagement.selection.mapper;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 선택지에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SelectionMapper {


    /**
     * 선택지 리스트를 등록하는 쿼리 입니다.
     *
     * @param surveyQuestionNo       설문 문항 번호
     * @param selectionCreateDtoList 선택지 정보를 담은 리스트
     * @author : 강명관
     */
    void insertSelectionList(@Param("surveyQuestionNo") long surveyQuestionNo,
                             @Param("selectionCreateDtoList")
                             List<SelectionCreateDto> selectionCreateDtoList);

    /**
     * 설문 번호에 대한 선택지들을 삭제하는 쿼리 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    void deleteSelectionsBySurveyNo(@Param("surveyNo") long surveyNo);
}
