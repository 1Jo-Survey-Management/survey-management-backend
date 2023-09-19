package com.douzone.surveymanagement.selection.mapper;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SelectionMapper {

    void insertSelection(SelectionCreateDto selectionCreateDto);
}
