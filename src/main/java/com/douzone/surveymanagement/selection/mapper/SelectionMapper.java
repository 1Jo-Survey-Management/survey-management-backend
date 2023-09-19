package com.douzone.surveymanagement.selection.mapper;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 선택지에 대한 mybatis 매퍼 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SelectionMapper {

    void insertSelection(SelectionCreateDto selectionCreateDto);
}
