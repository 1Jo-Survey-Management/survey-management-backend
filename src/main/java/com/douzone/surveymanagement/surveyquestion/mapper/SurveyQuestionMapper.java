package com.douzone.surveymanagement.surveyquestion.mapper;

import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface SurveyQuestionMapper {

    void insertSurveyQuestion(SurveyQuestionCreateDto surveyQuestionCreateDto);
}
