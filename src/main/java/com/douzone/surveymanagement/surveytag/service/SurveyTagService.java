package com.douzone.surveymanagement.surveytag.service;

import java.util.List;

/**
 * 설문 태그에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface SurveyTagService {

    /**
     * 하나의 설문에 대해 모든 설문을 등록하는 메서드 입니다.
     *
     * @param surveyNo
     * @param surveyTagNoList
     * @author : 강명관
     */
    void insertAllSurveyTag(long surveyNo, List<Integer> surveyTagNoList);

    /**
     * 설문 번호를 통해 해당 설문의 태그를 모두 삭제하는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    void deleteSurveyTags(long surveyNo);

    /**
     * 설문의 기존의 태그를 삭제하고 새롭게 추가하는 메서드 입니다.
     *
     * @param surveyNo        설문 번호
     * @param surveyTagNoList 설문 태그 번호 리스트
     * @author : 강명관
     */
    void updateSurveyTags(long surveyNo, List<Integer> surveyTagNoList);
}
