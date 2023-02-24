package com.group5.stackoverflow.vote.controller;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.vote.dto.VoteDto;
import com.group5.stackoverflow.vote.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping
@Valid
@Slf4j
@CrossOrigin(value = {"http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com",
        "http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com/"})
public class VoteController {
    private final VoteService voteService;
    private final JwtTokenizer jwtTokenizer;

    public VoteController(VoteService voteService, JwtTokenizer jwtTokenizer) {
        this.voteService = voteService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{question-id}/upcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.QuestionResponse postQuestionUpCount(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("question-id") @Positive Long questionId) {
        return voteService.saveQuestionVote(questionId, jwtTokenizer.getMemberId(token), 1);

    }

    @PostMapping("/questions/{question-id}/downcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.QuestionResponse postQuestionDownCount(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("question-id") @Positive Long questionId) {
        return voteService.saveQuestionVote(questionId, jwtTokenizer.getMemberId(token), -1);
    }

    @PostMapping("/answers/{answer-id}/upcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.AnswerResponse postAnswerUpCount(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("answer-id") @Positive Long answerId) {
        return voteService.saveAnswerVote(answerId, jwtTokenizer.getMemberId(token), 1);
    }

    @PostMapping("/answers/{answer-id}/downcount")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto.AnswerResponse postAnswerDownCount(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("answer-id") @Positive Long answerId) {
        return voteService.saveAnswerVote(answerId, jwtTokenizer.getMemberId(token), -1);
    }
}
