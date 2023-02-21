package com.group5.stackoverflow.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class AnswerDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private Long memberId;
        private Long questionId;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long memberId;
        private Long questionId;
        private Long answerId;
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private Long answerId;
        private Long memberId;
        private Long questionId;
        private String memberName;
        private String content;
        private Integer voteCount;
    }



}
