package com.douzone.surveymanagement.selection.service;

import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;

/**
 * 선택지에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface SelectionService {


    /**
     * 선택지를 등록하는 메서드 입니다.
     *
     * @param selectionCreateDto 선택지를 생성하기 위한 정보를 담은 Dto
     * @author : 강명관
     */
    void insertSelection(SelectionCreateDto selectionCreateDto);
}
