package com.douzone.surveymanagement.statistics.mapper;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectMapper {
    List<SelectDto> findSelection(int surveyNo, int surveyQuestionNo);
}
