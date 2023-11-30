package com.douzone.surveymanagement.statistics.service;

import com.douzone.surveymanagement.statistics.dto.SelectDto;
import java.util.List;

public interface SelectService {
    List<SelectDto> readSelectionAll(long surveyNo);
}
