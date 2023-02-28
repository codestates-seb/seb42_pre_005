package com.group5.stackoverflow.answer.dto;

import lombok.*;

import javax.swing.plaf.SpinnerUI;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private Long questionId;
        private Long memberId;

        @NotBlank(message = "답변 내용은 공백이 아니여야 합니다.")
        private String content;

        public void addMemberId(Long memberId) {
            this.memberId = memberId;
        }

        public void addQuestionId(Long questionId) {
            this.questionId = questionId;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long memberId;
        private Long answerId;
        private Long questionId;

        @NotBlank
        private String content;

        public void addMemberId(Long memberId) {
            this.memberId = memberId;
        }

        public void addAnswerId(Long answerId) {
            this.answerId = answerId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PatchVote {

        private Long answerId;
        private Long voteId;

        public void addAnswerId(Long answerId) {
            this.answerId = answerId;
        }
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long answerId;
        private Long memberId;
        private Long questionId;
        private String name;
        private String content;
        private int voteCount;
    }



}
