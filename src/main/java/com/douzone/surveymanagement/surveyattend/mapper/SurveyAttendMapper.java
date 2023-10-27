package com.douzone.surveymanagement.surveyattend.mapper;

import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendDTO;
import com.douzone.surveymanagement.surveyattend.dto.request.SurveyAttendSubmitDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 설문 참여 관련 데이터 작업을 관리하는 Mapper 입니다.
 *
 * @author : 박창우
 * @since : 1.0
 **/
@Mapper
public interface SurveyAttendMapper {

    /**
     * 설문 참여 데이터를 가져옵니다.
     *
     * @return 설문 참여 데이터를 포함한 {@link SurveyAttendDTO}의 리스트를 반환합니다.
     */
    List<SurveyAttendDTO> selectSurveyAttendData();

    /**
     * 설문 참여에 대한 새로운 레코드를 삽입합니다.
     *
     * @param dto 삽입할 설문 참여 데이터입니다.
     * @return 영향을 받은 행의 수를 반환합니다.
     */
    int insertSurveyAttend(SurveyAttendSubmitDTO dto);

    /**
     * 설문에 대한 객관식 답변을 삽입합니다.
     *
     * @param dto 삽입할 객관식 답변 데이터입니다.
     * @return 영향을 받은 행의 수를 반환합니다.
     */
    int insertObjectiveAnswer(SurveyAttendSubmitDTO dto);

    /**
     * 설문에 대한 주관식 답변을 삽입합니다.
     *
     * @param dto 삽입할 주관식 답변 데이터입니다.
     * @return 영향을 받은 행의 수를 반환합니다.
     */
    int insertSurveySubjectiveAnswer(SurveyAttendSubmitDTO dto);

    /**
     * 설문 답변 선택지를 삽입합니다.
     *
     * @param dto 삽입할 설문 답변 선택지 데이터입니다.
     * @return 영향을 받은 행의 수를 반환합니다.
     */
    int insertSurveyAnswerSelection(SurveyAttendSubmitDTO dto);
}
