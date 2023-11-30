package com.douzone.surveymanagement.surveyattend.service;

import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendDTO;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendSubmitDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 설문 참여 서비스 인터페이스입니다.
 *
 * @author : 박창우
 * @since : 1.0
 **/
public interface SurveyAttendService {

    /**
     * 설문 정보를 가져옵니다.
     *
     * @return 설문 정보 목록
     */
    List<SurveyAttendDTO> selectSurveyAttendData(long surveyNo);

    /**
     * 설문 참여와 관련된 답변(주관식, 객관식)을 저장합니다.
     *
     * @param dtoList 설문 참여 및 답변 데이터
     */
    void saveSurveyAndAnswers(List<SurveyAttendSubmitDTO> dtoList);

    /**
     * 주어진 설문 번호에 대한 마감일을 반환합니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문의 마감일
     */
    LocalDateTime getSurveyClosingTime(long surveyNo);
}
