package com.group5.stackoverflow.vote.controller;

import com.group5.stackoverflow.vote.dto.VoteDto;
import com.group5.stackoverflow.vote.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/votes")
@Valid
@Slf4j
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/questions/{question-id}/upcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.QuestionResponse postQuestionUpCount(
            @PathVariable("question-id") @Positive Long questionId) {
        return null;
    }

    @PostMapping("/questions/{question-id}/downcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.QuestionResponse postQuestionDownCount(
            @PathVariable("question-id") @Positive Long questionId) {
        return null;
    }

    @PostMapping("/answers/{answer-id}/upcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.AnswerResponse postAnswerUpCount(
            @PathVariable("answer-id") @Positive Long questionId) {
        return null;
    }

    @PostMapping("/answers/{answer-id}/downcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.AnswerResponse postAnswerDownCount(
            @PathVariable("answer-id") @Positive Long questionId) {
        return null;
    }
}
