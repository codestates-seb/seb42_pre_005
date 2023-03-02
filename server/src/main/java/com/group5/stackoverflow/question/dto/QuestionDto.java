package com.group5.stackoverflow.question.dto;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank
        private String content;

        private Long memberId;

        @NotNull
        private List<String> tagNames;

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

        @NotNull
        private List<String> tagNames;

        public void addQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PatchVote {
        private Long voteId;
        private Long questionId;

        public void addQuestionId(long questionId) {
            this.questionId = questionId;
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long questionId;
        private String title;
        private String content;
        private Long memberId;
        private String name;
        private int voteCount;
        private int views;
        private LocalDateTime createdAt;
        private List<TagDto.ResponseDto> tagResponseDtos;
        private List<AnswerDto.Response> answerResponseDtos;
    }
}
