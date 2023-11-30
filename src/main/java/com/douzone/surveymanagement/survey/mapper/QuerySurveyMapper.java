package com.douzone.surveymanagement.survey.mapper;

import com.douzone.surveymanagement.survey.dto.response.SurveyDetailInfoDto;
import com.douzone.surveymanagement.survey.dto.response.SurveyDetailsDto;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 설문에 대한 조회를 담당하는 시그니쳐를 정의하는 인터페이스입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Mapper
public interface QuerySurveyMapper {

    /**
     * 설문에 대한 모든 정보를 가져오는 쿼리 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문에 대한 모든 정보를 담은 Dto
     * @author : 강명관
     */
    SurveyDetailsDto selectSurveyDetailsBySurveyNo(@Param("surveyNo") long surveyNo);

    /**
     * 설문의 이미지가 저장된 경로를 가져오는 쿼리 입니다.
     *
     * @param surveyNo 설문 번호
     * @return 설문 이미지 저장된 경로
     * @author : 강명관
     */
    String selectSurveyImageBySurveyNo(@Param("surveyNo") long surveyNo);

    /**
     * 설문 번호와 유저 번호가 설문 작성자인지 확인하는 쿼리
     *
     * @param userNo   유저 번호
     * @param surveyNo 설문 번호
     * @return 설문의 작성자와 해당 유저가 동일하면 true, 아니다면 false
     * @author : 강명관
     */
    boolean selectSurveyCreatedByUser(@Param("userNo") long userNo,
                                      @Param("surveyNo") long surveyNo);

    List<SurveyDetailInfoDto> selectWeeklySurvey(long userNo);

    List<SurveyDetailInfoDto> selectRecentSurvey(long userNo);

    List<SurveyDetailInfoDto> closingSurvey(long userNo);

    List<SurveyDetailInfoDto> selectAllSurvey(HashMap<String, Object> pageUserNo);

    List<SurveyDetailInfoDto> selectClosingSurvey(HashMap<String, Object> pageUserNo);

    List<SurveyDetailInfoDto> selectPostSurvey(HashMap<String, Object> pageUserNo);

    List<SurveyDetailInfoDto> searchSurvey(HashMap<String, Object> searchKeyword);

    /**
     * 설문 번호를 통해 설문의 상세 정보를 가져오는 쿼리 입니다.
     *
     * @param userNo   유저 번호
     * @param surveyNo 설문번호
     * @return {@link Optional<SurveyDetailInfoDto>}
     * @author : 강명관
     */
    Optional<SurveyDetailInfoDto> selectOneSurveyBySurveyNo(@Param("userNo") long userNo,
                                                            @Param("surveyNo") long surveyNo);
}
