package com.douzone.surveymanagement.survey.service;

import com.douzone.surveymanagement.survey.dto.request.SurveyInfoCreateDto;
import com.douzone.surveymanagement.survey.dto.request.SurveyInfoUpdateDto;
import com.douzone.surveymanagement.surveyquestion.dto.request.SurveyQuestionCreateDto;
import java.util.List;

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
     * @return 설문 등록된 PK
     * @author : 강명관
     */
    long insertSurveyInfo(SurveyInfoCreateDto surveyInfoCreateDto);


    /**
     * 전체 설문을 등록하는 메서드 입니다.
     *
     * @param surveyInfoCreateDto         설문에 대한 정보를 갖고 있는 Dto
     * @param surveyQuestionCreateDtoList 설문에 대한 문항들에 대한 정보를 담고 있는 Dto 리스트
     * @author : 강명관
     */
    void insertSurvey(SurveyInfoCreateDto surveyInfoCreateDto,
                      List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList);


    /**
     * 설문의 상태가 진행중이고, 마감일이 오늘(00시 00분)보다 이전일 경우에 해당 설문을 마감 상태로 변경시키는 메서드 입니다.
     *
     * @author : 강명관
     */
    void updateSurveyStatusToDeadline();


    /**
     * 설문에 대한 정보를 수정하는 메서드 입니다.
     *
     * @param surveyInfoUpdateDto 설문 정보 수정에 필요한 데이터를 담은 Dto
     * @author : 강명관
     */
    void updateSurveyInfo(SurveyInfoUpdateDto surveyInfoUpdateDto);

    /**
     * 설문을 수정하는 메서드 입니다.
     * 기존 설문에 등록되어 있던 문항, 선택지들을 삭제하고
     * 새롭게 새로운 문항들을 등록합니다.
     *
     * @param surveyInfoUpdateDto         설문에 대한 정보를 담은 Dto
     * @param surveyQuestionCreateDtoList 설문의 문항과 선택지 리스트
     * @author : 강명관
     */
    void updateSurvey(SurveyInfoUpdateDto surveyInfoUpdateDto,
                      List<SurveyQuestionCreateDto> surveyQuestionCreateDtoList);

    /**
     * 설문의 상태를 설문의 번호를 통해 게시 상태로 변경하는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문의 상태가 변경되었을 경우 true, 변경되지 않았을 경우 false를 반환합니다.
     * @author : 강명관
     */
    boolean updateSurveyStatusToPostInProgress(long surveyNo);
}
