package com.douzone.surveymanagement.statistics.dto;

import com.douzone.surveymanagement.questiontype.domain.QuestionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelectDto {
        private long surveyNo;
        private String surveyTitle;
        private String surveyQuestionTitle;
        private int questionTypeNo;
        private long selectionNo;
        private String selectionValue;
        private int selectionCount;
        private int questionAttendCount;

        }
