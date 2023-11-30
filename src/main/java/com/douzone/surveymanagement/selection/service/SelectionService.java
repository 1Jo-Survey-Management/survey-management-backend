package com.douzone.surveymanagement.selection.service;

import com.douzone.surveymanagement.questiontype.enums.QuestionTypeEnum;
import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import java.util.List;

/**
 * 선택지에 대한 비즈니스 로직을 정의하는 인터페이스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public interface SelectionService {

    /**
     * 선택지 리스트를 저장하기 위한 리스트 입니다.
     *
     * @param questionTypeEnum       해당 선택지의 문항 타입 Enum
     * @param questionNoList         문항들을 저장하고 나온 PK 리스트
     * @param surveyQuestionNo       해당 선택지에 대한 문항 번호
     * @param selectionCreateDtoList 선택지를 저장하기 위한 Dto 리스트
     * @author : 강명관
     */
    void insertSelectionList(QuestionTypeEnum questionTypeEnum,
                             List<Long> questionNoList,
                             long surveyQuestionNo,
                             List<SelectionCreateDto> selectionCreateDtoList);

    /**
     * 설문 번호에 해당하는 모든 선택지를 삭제하는 메서드 입니다.
     *
     * @param surveyNo 설문 번호
     * @author : 강명관
     */
    void deleteAllSelection(long surveyNo);
}
