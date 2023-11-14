package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


/**
 * 설문 조회에 대한 비즈니스 로직을 정의하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class QuerySurveyServiceImpl implements QuerySurveyService {

    private final QuerySurveyMapper querySurveyMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public SurveyDetailsDto findSurveyDetails(long surveyNo) {
        return querySurveyMapper.selectSurveyDetailsBySurveyNo(surveyNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findSurveyImageBySurveyNo(long surveyNo) {
        return querySurveyMapper.selectSurveyImageBySurveyNo(surveyNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSurveyCreatedByUser(long userNo, long surveyNo) {
        return querySurveyMapper.selectSurveyCreatedByUser(userNo, surveyNo);
    }

    @Override
    public List<SurveyDetailInfoDto> readWeeklySurvey(long userNo) {
        return querySurveyMapper.selectWeeklySurvey(userNo);
    }

    @Override
    public List<SurveyDetailInfoDto> readRecentSurvey(long userNo) {
        return querySurveyMapper.selectRecentSurvey(userNo);
    }

    @Override
    public List<SurveyDetailInfoDto> readClosingSurvey(long userNo) {
        return querySurveyMapper.closingSurvey(userNo);
    }

    @Override
    public List<SurveyDetailInfoDto> getSurveyAll(int page, long userNo) {
        return querySurveyMapper.selectAllSurvey(pageAndUserNo(page, userNo));
    }

    @Override
    public List<SurveyDetailInfoDto> selectClosing(int page, long userNo) {
        return querySurveyMapper.selectClosingSurvey(pageAndUserNo(page, userNo));
    }

    @Override
    public List<SurveyDetailInfoDto> selectPost(int page, long userNo) {
        return querySurveyMapper.selectPostSurvey(pageAndUserNo(page, userNo));
    }

    private int showNextPage(int page) {
        int nextPage = page * 20;
        return nextPage;
    }

    private HashMap<Integer, Long> pageAndUserNo(int page, long userNo){
        HashMap<Integer, Long> pageUser = new HashMap<>();
        pageUser.put(showNextPage(page), userNo);
        return pageUser;
    }
}
