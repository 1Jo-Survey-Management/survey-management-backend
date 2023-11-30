package com.douzone.surveymanagement.selection.service.imp;

import com.douzone.surveymanagement.questiontype.enums.QuestionTypeEnum;
import com.douzone.surveymanagement.selection.dto.reqeust.SelectionCreateDto;
import com.douzone.surveymanagement.selection.mapper.SelectionMapper;
import com.douzone.surveymanagement.selection.service.SelectionService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 선택지에 대한 비즈니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
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
    public void insertSelectionList(QuestionTypeEnum questionTypeEnum,
                                    List<Long> questionNoList,
                                    long surveyQuestionNo,
                                    List<SelectionCreateDto> selectionCreateDtoList) {

        if (!questionTypeEnum.isSelection()) {
            return;
        }

        if (Objects.equals(questionTypeEnum, QuestionTypeEnum.MOVABLE_SINGLE_SELECTION)) {
            calcMovedQuestionNo(questionNoList, selectionCreateDtoList);
        }

        selectionMapper.insertSelectionList(surveyQuestionNo, selectionCreateDtoList);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAllSelection(long surveyNo) {
        selectionMapper.deleteSelectionsBySurveyNo(surveyNo);
    }

    /**
     * 설문이동 번호를 계산하고 넣어주기 위한 메서드 입니다.
     *
     * @param questionNoList         문항들이 저장되고 나온 문항 PK 번호 리스트 입니다.
     * @param selectionCreateDtoList 선택지를 저장하기 위한 Dto 리스트 입니다.
     * @author : 강명관
     */
    private static void calcMovedQuestionNo(List<Long> questionNoList,
                                            List<SelectionCreateDto> selectionCreateDtoList) {
        for (SelectionCreateDto selectionCreateDto : selectionCreateDtoList) {

            if (selectionCreateDto.isEndOfSurvey()) {
                continue;
            }

            int moveQuestionIndex = (int) (selectionCreateDto.getSurveyQuestionMoveNo() - 1);
            selectionCreateDto.setSurveyQuestionMoveNo(
                questionNoList.get(moveQuestionIndex)
            );
        }
    }
}
