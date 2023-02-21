package com.group5.stackoverflow.answer.controller;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.dto.SingleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequestMapping
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerRepository answerRepository;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService,
                            AnswerRepository answerRepository, AnswerMapper mapper) {
        this.answerService = answerService;
        this.answerRepository = answerRepository;
        this.mapper = mapper;
    }

    @PostMapping("/questions/{question-id}/answers")
    public ResponseEntity postAnswer(@PathVariable("question-id") Long questionId,
                                     @Valid @RequestBody AnswerDto.Post requestBody) {
        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
        Answer response = answerService.createAnswer(answer, questionId);

        return new ResponseEntity(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") Long answerId,
                                      @Valid @RequestBody AnswerDto.Patch requestBody) {
        Answer response = answerService.updateAnswer(answerId, mapper.answerPatchDtoToAnswer(requestBody));

        return new ResponseEntity(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId) {

        answerService.deleteAnswer(answerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
