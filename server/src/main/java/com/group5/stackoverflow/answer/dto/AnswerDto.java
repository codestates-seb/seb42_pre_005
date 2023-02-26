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
        private Long memberId;

        @NotBlank(message = "답변 내용은 공백이 아니여야 합니다.")
        private String content;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {

        private Long memberId;
        private Long answerId;
        private Long questionId;

        @NotBlank
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private Long answerId;
        private Long memberId;
        private Long questionId;
        private String name;
        private String content;
        private int voteCount;
    }



}
