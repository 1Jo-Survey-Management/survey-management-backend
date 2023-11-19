package com.douzone.surveymanagement.statistics.service.impl;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import com.douzone.surveymanagement.statistics.mapper.SelectMapper;
import com.douzone.surveymanagement.statistics.service.SelectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    private final SelectMapper selectMapper;

    public SelectServiceImpl(SelectMapper selectMapper) {
        this.selectMapper = selectMapper;
    }


    @Override
    public List<SelectDto> readSelectionAll(long surveyNo) {
        return selectMapper.readSelectionAll(surveyNo);
    }
}
