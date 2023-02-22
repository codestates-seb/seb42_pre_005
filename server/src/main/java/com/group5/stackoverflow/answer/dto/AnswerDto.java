package com.group5.stackoverflow.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {

        private Long questionId;
        @NotNull
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {

        private Long answerId;
        private Long questionId;

        @NotBlank
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {

        private Long answerId;
        private Long questionId;
        private String memberName;
        private String content;
        private int voteCount;
    }



}
