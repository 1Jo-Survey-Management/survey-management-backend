package com.douzone.surveymanagement.statistics.service.impl;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.mapper.SelectMapper;
import com.douzone.surveymanagement.statistics.service.SelectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {
    public SelectServiceImpl(SelectMapper selectMapper) {
        this.selectMapper = selectMapper;
    }

    private SelectMapper selectMapper;
    @Override
    public List<SelectDto> readSelection(int surveyNo, int surveyQuestionNo) {
        return selectMapper.findSelection(surveyNo, surveyQuestionNo);
    }

    @Override
    public List<SelectDto> readSelectionAll(int surveyNo) {
        return selectMapper.readSelectionAll(surveyNo);
    }
}
