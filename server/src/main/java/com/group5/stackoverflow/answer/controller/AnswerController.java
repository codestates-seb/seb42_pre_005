package com.group5.stackoverflow.answer.controller;

import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.answer.service.AnswerService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/answers")
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

    @PostMapping
    public ResponseEntity postAnswer() {
        return null;
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer() {
        return null;
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer() {
        return null;
    }
}
