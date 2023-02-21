package com.group5.stackoverflow.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoteDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionResponse {
        private Long questionId;
        private Long memberId;
        private int voteCount;
        private boolean upCount;
        private boolean downCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerResponse {
        private Long answerId;
        private Long memberId;
        private int voteCount;
        private boolean upCount;
        private boolean downCount;
    }
}
