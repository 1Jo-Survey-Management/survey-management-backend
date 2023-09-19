package com.douzone.surveymanagement.selection.service.imp;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import com.douzone.surveymanagement.selection.mapper.SelectionMapper;
import com.douzone.surveymanagement.selection.service.SelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 선택지에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectionServiceImpl implements SelectionService {

    private final SelectionMapper selectionMapper;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void insertSelection(SelectionCreateDto selectionCreateDto) {
        selectionMapper.insertSelection(selectionCreateDto);
    }
}
