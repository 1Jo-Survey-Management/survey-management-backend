package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 설문에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface CommandSurveyService {

    /**
     * 섦문에 대한 정보를 등록하는 메서드 입니다.
     *
     * @param surveyInfoCreateDto 설문에 대한 정보를 담은 Dto
     * @param surveyImage 설문 대표 이미지
     * @return 설문 등록된 PK
     * @author : 강명관
     */
    long insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto, MultipartFile surveyImage);


    /**
     * 전체 설문을 등록하는 메서드 입니다.
     *
     * @param surveyInfoCreateDto 설문에 대한 정보를 갖고 있는 Dto
     * @param surveyQuestionCreateDtoList 설문에 대한 문항들에 대한 정보를 담고 있는 Dto 리스트
     * @param surveyImage 설문에 대한 대표 이미지
     * @author : 강명관
     */
    void insertSurvey(SurveyInfoCreateDto surveyInfoCreateDto,
                      List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList,
                      MultipartFile surveyImage);


    /**
     * 설문의 상태가 진행중이고, 마감일이 오늘(00시 00분)보다 이전일 경우에 해당 설문을 마감 상태로 변경시키는 메서드 입니다.
     *
     * @author : 강명관
     */
    void updateSurveyStatusToDeadline();

}
