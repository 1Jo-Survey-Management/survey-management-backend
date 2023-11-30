package com.douzone.surveymanagement.mysurvey.service;

import com.douzone.surveymanagement.mysurvey.dto.request.MySurveyDTO;
import java.util.List;

/**
 * 마이페이지 설문목록 서비스 인터페이스입니다.
 */
public interface MySurveyService {

    /**
     * 사용자가 작성한 설문 목록을 정렬하여 가져옵니다.
     *
     * @param userNo 사용자 번호
     * @return 사용자가 작성한 설문 목록
     */
    List<MySurveyDTO> selectMySurveysWithSorting(long userNo);

    /**
     * 사용자가 참여한 설문 목록을 정렬하여 가져옵니다.
     *
     * @param userNo 사용자 번호
     * @return 사용자가 참여한 설문 목록
     */
    List<MySurveyDTO> selectMyParticipatedSurveys(long userNo);

    /**
     * 사용자가 작성 중인 설문 중 삭제할 설문을 삭제합니다.
     *
     * @param mySurveyDTO 설문 객체 DTO
     * @return 삭제 성공 여부 (true: 삭제 성공, false: 삭제 실패)
     */
    boolean deleteMySurveyInProgress(MySurveyDTO mySurveyDTO);
}
