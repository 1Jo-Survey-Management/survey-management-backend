//package com.douzone.surveymanagement.survey.service.impl;
//
//import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
//import com.douzone.surveymanagement.survey.mapper.QuerySurveyMapper;
//import com.douzone.surveymanagement.survey.service.QuerySurveyService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.awt.print.Pageable;
//import java.util.List;
//
///**
// *
// * @{inheritDoc}
// *
// *
// */
//@Service
//public class QuerySurveyServiceImpl implements QuerySurveyService {
//    private QuerySurveyMapper querySurveyMapper;
//
//    private int showNextPage(int page){
//        int nextPage = page * 20;
//        return nextPage;
//    }
//
//    public QuerySurveyServiceImpl(QuerySurveyMapper querySurveyMapper) {
//        this.querySurveyMapper = querySurveyMapper;
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> readWeeklySurvey() {
//        return querySurveyMapper.selectWeeklySurvey();
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> readRecentSurvey() {
//
//        return querySurveyMapper.selectRecentSurvey();
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> readClosingSurvey() {
//        return querySurveyMapper.closingSurvey();
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> getSurveyAll(int page) {
//        return querySurveyMapper.selectAllSurvey(showNextPage(page));
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> selectClosing(int page) {
//        return querySurveyMapper.selectClosingSurvey(showNextPage(page));
//    }
//
//    @Override
//    public List<SurveyDetailInfoDto> selectPost(int page) {
//        return querySurveyMapper.selectPostSurvey(showNextPage(page));
//    }
//}
