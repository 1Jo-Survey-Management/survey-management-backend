package com.douzone.surveymanagement.surveytag.service.impl;

import com.douzone.surveymanagement.surveytag.dto.request.SurveyTagCreateDto;
import com.douzone.surveymanagement.surveytag.mapper.SurveyTagMapper;
import com.douzone.surveymanagement.surveytag.service.SurveyTagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문 태그에 대한 비즈니스 로직을 담당하는 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyTagServiceImpl implements SurveyTagService {

    private final SurveyTagMapper surveyTagMapper;

    private static final int NEXT_DEFAULT_INDEX = 1;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertAllSurveyTag(long surveyNo, List<Integer> surveyTagNoList) {
        surveyTagNoList.forEach(tagNo ->
            surveyTagMapper.insertSurveyTag(
                new SurveyTagCreateDto(surveyNo, tagNo + NEXT_DEFAULT_INDEX)
            )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteSurveyTags(long surveyNo) {
        surveyTagMapper.deleteAllSurveyTagBySurveyNo(surveyNo);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateSurveyTags(long surveyNo, List<Integer> surveyTagNoList) {
        deleteSurveyTags(surveyNo);
        insertAllSurveyTag(surveyNo, surveyTagNoList);
    }
}
