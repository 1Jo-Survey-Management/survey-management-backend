package com.douzone.surveymanagement.statistics.mapper;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SelectMapper {
    List<SelectDto> readSelectionAll(@Param(value = "surveyNo") long surveyNo);
}
