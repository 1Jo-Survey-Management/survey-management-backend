package com.douzone.surveymanagement.statistics.dto;

import com.douzone.surveymanagement.questiontype.domain.QuestionType;
import lombok.*;


@Builder
@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SelectDto {
        private final long surveyNo;
        private final String surveyTitle;
        private final String userNickname;
        private final String surveyPostAt;
        private final String surveyClosingAt;
        private final String surveyQuestionTitle;
        private final String surveyQuestionNo;
        private final int questionTypeNo;
        private final long selectionNo;
        private final String selectionValue;
        private final int selectionCount;
        private final int questionAttendCount;
        private final String surveySubjectiveAnswer;

        }
