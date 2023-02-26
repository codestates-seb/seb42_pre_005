package com.group5.stackoverflow.question.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.mapper.QuestionMapper;
import com.group5.stackoverflow.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/questions")
@Validated
@Slf4j
public class QuestionController {
    public final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService,
                              QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    // 질문 생성
//    @PostMapping
//    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
//        Question question = questionMapper.questionPostToQuestion(requestBody);
//
//        Question createdQuestion = questionService.createQuestion(question);
//        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, createdQuestion.getQuestionId());
//
//        return ResponseEntity.created(location).build();
//    }

    // 질문 수정
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive Long questionId,
                                        @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.addQuestionId(questionId);

        Question question = questionService.updateQuestion(questionMapper.questionPatchToQuestion(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(questionMapper.questionToQuestionResponse(question)),
                HttpStatus.OK);
    }

    // 질문 조회 1건
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive Long questionId) {
        Question question = questionService.findQuestion(questionId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(questionMapper.questionToQuestionResponse(question)),
                HttpStatus.OK);
    }

    // 질문 전체 조회
    @GetMapping
    public ResponseEntity getQuestions(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam(required = false, defaultValue = "newest") String tab) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size, tab);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(questionMapper.questionsToQuestionResponses(questions), pageQuestions),
                HttpStatus.OK);
    }

    // 검색에 의한 질문 조회
    @GetMapping("/search")
    public ResponseEntity searchQuestion(@RequestParam String keyword,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size) {
        Page<Question> pageQuestions = questionService.searchQuestion(page - 1, size, keyword);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(questionMapper.questionsToQuestionResponses(questions), pageQuestions),
                HttpStatus.OK);
    }

    // 추천수 updown 기능
    @PatchMapping("/{question-id}/vote")
    public ResponseEntity patchQuestionVote(@PathVariable("question-id") @Positive Long questionId,
                                            @RequestParam String updown,
                                            @Valid @RequestBody QuestionDto.PatchVote requestBody ) {
        requestBody.addQuestionId(questionId);
        Question question =
                questionService.updateVote(requestBody.getQuestionId(), updown);

        return new ResponseEntity<>(
                new SingleResponseDto<>(questionMapper.questionToQuestionResponse(question)),
                HttpStatus.OK);
    }

    // 질문 삭제
    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive Long questionId) {
        questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
