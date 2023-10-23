package com.douzone.surveymanagement.statistics.dto;

import com.douzone.surveymanagement.questiontype.domain.QuestionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class SelectDto {
        private long surveyNo;
        private String surveyTitle;
        private String userNickname;
        private LocalDateTime surveyPostAt;
        private LocalDate surveyClosingAt;
        private long surveyQuestionNo;
        private String surveyQuestionTitle;
        private int questionTypeNo;
        private long selectionNo;
        private String selectionValue;
        private int selectionCount;
        private String surveySubjectiveAnswer;

        }
