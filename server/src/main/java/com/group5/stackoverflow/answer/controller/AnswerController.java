package com.group5.stackoverflow.answer.controller;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@Validated
@RequestMapping
@CrossOrigin(value = {"http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com",
        "http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com/"})
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public AnswerController(AnswerService answerService, AnswerMapper mapper, JwtTokenizer jwtTokenizer) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{question-id}/answers")
    public ResponseEntity postAnswer(@RequestHeader(name = "Authorization") String token,
                                     @PathVariable("question-id") @Positive Long questionId,
                                     @Valid @RequestBody AnswerDto.Post requestBody){



        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
        Answer response = answerService.createAnswer(questionId, answer, jwtTokenizer.getMemberId(token));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answer-id}")
    public ResponseEntity patchAnswer(@RequestHeader(name = "Authorization") String token,
                                      @PathVariable("answer-id") @Positive Long answerId,
                                      @Valid @RequestBody AnswerDto.Patch requestBody) {
        Answer answer = mapper.answerPatchDtoToAnswer(requestBody);
        answer.setAnswerId(answerId);
        Answer response = answerService.updateAnswer(answer, jwtTokenizer.getMemberId(token));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@RequestHeader(name = "Authorization") String token,
                                       @PathVariable("answer-id") @Positive Long answerId) {

        answerService.deleteAnswer(answerId, jwtTokenizer.getMemberId(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
