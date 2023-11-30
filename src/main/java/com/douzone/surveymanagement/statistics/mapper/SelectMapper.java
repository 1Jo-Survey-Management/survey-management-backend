package com.douzone.surveymanagement.statistics.mapper;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SelectMapper {

    List<SelectDto> readSelectionAll(@Param(value = "surveyNo") long surveyNo);
}
