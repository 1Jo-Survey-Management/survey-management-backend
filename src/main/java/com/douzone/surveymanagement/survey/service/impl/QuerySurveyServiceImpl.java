package com.douzone.surveymanagement.survey.service.impl;

import com.douzone.surveymanagement.common.exception.NotFoundElementException;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
import com.douzone.surveymanagement.survey.service.QuerySurveyService;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
@Transactional(readOnly = true)
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

    @Override
    public List<SurveyDetailInfoDto> searchSurveyByKeyword(String searchWord, long userNo) {
        return querySurveyMapper.searchSurvey(searchWord(searchWord, userNo));
    }

    private int showNextPage(int page) {
        int nextPage = page * 12;
        return nextPage;
    }

    private HashMap<String, Object> pageAndUserNo(int page, long userNo) {
        HashMap<String, Object> pageUser = new HashMap<>();
        pageUser.put("page", showNextPage(page));
        pageUser.put("userNo", userNo);
        return pageUser;
    }

    private HashMap<String, Object> searchWord(String searchWord, long userNo) {
        HashMap<String, Object> searchKeyword = new HashMap<>();
        searchKeyword.put("searchWord", searchWord);
        searchKeyword.put("userNo", userNo);
        return searchKeyword;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SurveyDetailInfoDto findOneSurveyDetailsBySurveyNo(long userNo, long surveyNo) {
        return querySurveyMapper.selectOneSurveyBySurveyNo(userNo, surveyNo)
            .orElseThrow(() -> new NotFoundElementException("해당 설문을 찾을 수 없습니다."));
    }

}
