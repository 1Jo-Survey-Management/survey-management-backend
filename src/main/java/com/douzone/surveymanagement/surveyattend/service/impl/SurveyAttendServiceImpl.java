package com.douzone.surveymanagement.surveyattend.service.impl;

import com.douzone.surveymanagement.common.exception.NotFoundElementException;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendDTO;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendSubmitDTO;
import com.douzone.surveymanagement.surveyattend.exception.SurveyAttendException;
import com.douzone.surveymanagement.surveyattend.mapper.SurveyAttendMapper;
import com.douzone.surveymanagement.surveyattend.service.SurveyAttendService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 설문 참여 서비스 구현체입니다.
 *
 * @author : 박창우
 * @since : 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyAttendServiceImpl implements SurveyAttendService {

    private final SurveyAttendMapper surveyAttendMapper;

    /**
     * 설문 참여 데이터를 조회합니다.
     *
     * @return 설문 참여 데이터를 포함한 {@link SurveyAttendDTO}의 리스트를 반환합니다.
     */
    @Override
    public List<SurveyAttendDTO> selectSurveyAttendData(long surveyNo) {
        List<SurveyAttendDTO> surveyAttendDTOList =
            surveyAttendMapper.selectSurveyAttendData(surveyNo);

        if (surveyAttendDTOList.isEmpty()) {
            throw new NotFoundElementException("해당 설문을 찾을 수 없습니다.");
        }

        return surveyAttendDTOList;
    }

    /**
     * 설문과 그에 대한 답변들을 저장합니다.
     *
     * <p>
     * 이 메소드는 여러 단계의 데이터 저장 작업을 처리하기 때문에 트랜잭션을 사용하여 전체 작업이 성공하거나 전체 작업이 실패하도록 관리합니다.
     * </p>
     *
     * @param surveyAttendDTOList 저장할 설문과 답변들의 리스트입니다.
     * @throws SurveyAttendException 설문 데이터 저장 실패 시 예외를 발생시킵니다.
     */
    @Override
    @Transactional
    public void saveSurveyAndAnswers(List<SurveyAttendSubmitDTO> surveyAttendDTOList) {
        if (surveyAttendDTOList.isEmpty()) {
            throw new SurveyAttendException("No data to save.");
        }

        // 첫 번째 'DTO' 에서 설문 번호 가져오기
        long surveyNo = surveyAttendDTOList.get(0).getSurveyNo();

        // 마감 시간 검사
        if (!isBeforeClosingTime(surveyNo)) {
            throw new SurveyAttendException("The survey has already closed.");
        }

        // 설문 참여 데이터 저장
        SurveyAttendSubmitDTO firstDto = surveyAttendDTOList.get(0);
        int surveyAttendRowCount = surveyAttendMapper.insertSurveyAttend(firstDto);
        if (surveyAttendRowCount == 0) {
            throw new SurveyAttendException("Failed to save survey attend data.");
        }

        // 설문 참여번호 가져오기
        long surveyAttendNo = firstDto.getSurveyAttendNo();

        Set<Long> processedQuestions = new HashSet<>();

        for (SurveyAttendSubmitDTO surveyAttendSubmitDTO : surveyAttendDTOList) {
            Long currentQuestionNo = surveyAttendSubmitDTO.getSurveyQuestionNo();

            if (processedQuestions.contains(currentQuestionNo)) {
                continue;
            }

            saveSubjectiveAnswer(surveyAttendSubmitDTO, surveyAttendNo);
            saveObjectiveAnswer(surveyAttendDTOList, surveyAttendSubmitDTO, surveyAttendNo,
                currentQuestionNo);

            processedQuestions.add(currentQuestionNo);
        }
    }

    /**
     * 주관식 답변을 데이터베이스에 저장합니다.
     * <p>
     * 주관식 답변이 존재하고 유효한 경우 해당 답변을 데이터베이스에 저장합니다.
     * 저장에 실패할 경우 {@link SurveyAttendException}을 발생시킵니다.
     * </p>
     *
     * @param dto            주관식 답변 정보를 포함한 DTO
     * @param surveyAttendNo 설문 참여 번호
     * @throws SurveyAttendException 저장에 실패한 경우
     */
    private void saveSubjectiveAnswer(SurveyAttendSubmitDTO dto, long surveyAttendNo) {
        if (dto.getSurveySubjectiveAnswer() != null &&
            !dto.getSurveySubjectiveAnswer().trim().isEmpty()) {
            dto.setSurveyAttendNo(surveyAttendNo);
            int rowCount = surveyAttendMapper.insertSurveySubjectiveAnswer(dto);
            if (rowCount == 0) {
                throw new SurveyAttendException("Failed to save subjective answer data.");
            }
        }
    }

    /**
     * 객관식 답변을 데이터베이스에 저장합니다.
     * <p>
     * 제공된 'DTO' 의 문항 유형이 객관식인 경우 해당 답변과 관련된 선택 항목들을 데이터베이스에 저장합니다.
     * 저장에 실패할 경우 {@link SurveyAttendException}을 발생시킵니다.
     * </p>
     *
     * @param list              설문 응답 리스트
     * @param dto               현재 처리 중인 문항에 대한 응답 정보를 포함한 DTO
     * @param surveyAttendNo    설문 참여 번호
     * @param currentQuestionNo 현재 처리 중인 문항 번호
     * @throws SurveyAttendException 저장에 실패한 경우
     */
    private void saveObjectiveAnswer(List<SurveyAttendSubmitDTO> list, SurveyAttendSubmitDTO dto,
                                     long surveyAttendNo, long currentQuestionNo) {
        if (dto.getQuestionTypeNo() == 1 || dto.getQuestionTypeNo() == 2 ||
            dto.getQuestionTypeNo() == 3) {
            dto.setSurveyAttendNo(surveyAttendNo);
            int rowCount = surveyAttendMapper.insertObjectiveAnswer(dto);
            if (rowCount == 0) {
                throw new SurveyAttendException("Failed to save objective answer data.");
            }

            long surveyAnswerNo = dto.getSurveyAnswerNo();

            list.stream()
                .filter(d -> d.getSurveyQuestionNo() == currentQuestionNo)
                .forEach(d -> {
                    d.setSurveyAnswerNo(surveyAnswerNo);
                    int objectiveRowCount = surveyAttendMapper.insertSurveyAnswerSelection(d);
                    if (objectiveRowCount == 0) {
                        throw new SurveyAttendException(
                            "Failed to save objective answer selection data.");
                    }
                });
        }
    }

    /**
     * 주어진 설문 번호에 대해 설문 마감 시간을 검사하고, 현재 시간이 마감 시간 이전인지 확인합니다.
     *
     * @param surveyNo 검사할 설문의 번호입니다.
     * @return 현재 시간이 설문 마감 시간 이전인 경우 true, 그렇지 않은 경우 false를 반환합니다.
     */
    private boolean isBeforeClosingTime(long surveyNo) {
        LocalDateTime closingTime = surveyAttendMapper.surveyForbidSubmit(surveyNo);
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isBefore(closingTime);
    }

    @Override
    public LocalDateTime getSurveyClosingTime(long surveyNo) {
        return surveyAttendMapper.surveyForbidSubmit(surveyNo);
    }
}