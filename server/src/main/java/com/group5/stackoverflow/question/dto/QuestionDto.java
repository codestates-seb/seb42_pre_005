package com.group5.stackoverflow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.NotBlank;

public class QuestionDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank
        private String content;

        private Long memberId;

        public void addMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long questionId;


        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank
        private String content;

        public void addQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private Long questionId;
        private String title;
        private String content;
        private Long memberId;
        private String name;
        private int voteCount;
        private int views;
        private String tagName;
    }
}
