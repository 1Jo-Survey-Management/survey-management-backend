package com.douzone.surveymanagement.mysurvey.service.impl;

import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import com.douzone.surveymanagement.mysurvey.mapper.MySurveyMapper;
import com.douzone.surveymanagement.mysurvey.service.MySurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MySurveyServiceImpl implements MySurveyService {
    private final MySurveyMapper mySurveyMapper;

    @Override
    public List<MySurveyDTO> selectMySurveysWithSorting(long userNo) {
        List<MySurveyDTO> surveys = mySurveyMapper.selectMySurveysWithSorting(userNo);

        return mySurveyMapper.selectMySurveysWithSorting(userNo);
    }

    @Override
    public List<MySurveyDTO> selectMyParticipatedSurveys(long userNo) {

        return mySurveyMapper.selectMyParticipatedSurveys(userNo);
    }

    @Override
    @Transactional
    public int updateMySurveysInProgress(MySurveyDTO mySurveyDTO) {
        int isDeleted = 0;

        if (mySurveyDTO.getSurveyStatusNo() == 1) {
            isDeleted = mySurveyMapper.updateMySurveysInProgress(mySurveyDTO);
        }
        return isDeleted;
    }
}
