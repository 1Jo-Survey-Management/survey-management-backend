package com.douzone.surveymanagement.survey.scheduler;

import com.douzone.surveymanagement.survey.service.CommandSurveyService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 설문에 대한 스케쥴링 태스크를 정의하는 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SurveyScheduler {

    private final CommandSurveyService commandSurveyService;

    /**
     * 매일 자정 0시 0분 0초에 실행하기 위한 스케쥴러 입니다.
     * 매일 자정에 실행되어 설문의 상태를 바꾸는 메서드를 실행합니다.
     * log.debug() 의 경우 로깅과 진행되는 시간을 나타내기 위함입니다.
     *
     * @author : 강명관
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void executeChangeSurveyStatusToDeadline() {
        log.debug("executeChangeSurveyStatusToDeadline {}", LocalDateTime.now());
        commandSurveyService.updateSurveyStatusToDeadline();
    }

}
