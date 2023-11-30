package com.douzone.surveymanagement.mysurvey.service.impl;

import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import com.douzone.surveymanagement.mysurvey.mapper.MySurveyMapper;
import com.douzone.surveymanagement.mysurvey.service.MySurveyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySurveyServiceImpl implements MySurveyService {
    private final MySurveyMapper mySurveyMapper;

    @Override
    public List<MySurveyDTO> selectMySurveysWithSorting(long userNo) {
        return mySurveyMapper.selectMySurveysWithSorting(userNo);
    }

    @Override
    public List<MySurveyDTO> selectMyParticipatedSurveys(long userNo) {

        return mySurveyMapper.selectMyParticipatedSurveys(userNo);
    }

    @Override
    @Transactional
    public boolean deleteMySurveyInProgress(MySurveyDTO mySurveyDTO) {
        return mySurveyDTO.getSurveyStatusNo() == 1 &&
            mySurveyMapper.updateMySurveysInProgress(mySurveyDTO) == 1;
    }
}
