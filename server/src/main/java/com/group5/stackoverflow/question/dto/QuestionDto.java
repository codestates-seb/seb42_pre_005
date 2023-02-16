package com.group5.stackoverflow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class QuestionDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long questionId;
        private String title;
        private String content;

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private Long questionId;
        private String title;
        private String content;
        private int voteCount;
        private int viewed;
    }
}
