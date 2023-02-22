package com.group5.stackoverflow.answer.controller;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@Slf4j
@RestController
@Validated
@RequestMapping
public class AnswerController {

//    private final static String ANSWER_DEFAULT_URL = "/answers";
    private final AnswerService answerService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService,
                            AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }

//    @PostMapping("/{question-id}")
//    public ResponseEntity postAnswer(@PathVariable("question-id") Long questionId,
//                                     @Valid @RequestBody AnswerDto.Post requestBody) {
//        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
//
//        Answer createAnswer = answerService.createAnswer(answer, questionId);
//        URI location = UriCreator.createUri(ANSWER_DEFAULT_URL, createAnswer.getAnswerId());
//
//        return ResponseEntity.created(location).build();
//    }

    @PostMapping("/questions/{question-id}/answers")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive Long questionId,
                                     @Valid @RequestBody AnswerDto.Post requestBody){
        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
        Answer response = answerService.createAnswer(questionId, answer);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive Long answerId,
                                      @Valid @RequestBody AnswerDto.Patch requestBody) {
        Answer answer = mapper.answerPatchDtoToAnswer(requestBody);
        answer.setAnswerId(answerId);
        answerService.updateAnswer(answer);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(answer)), HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive Long answerId) {

        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
