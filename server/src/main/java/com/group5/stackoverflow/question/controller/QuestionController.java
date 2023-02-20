package com.group5.stackoverflow.question.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.mapper.QuestionMapper;
import com.group5.stackoverflow.question.service.QuestionService;
import com.group5.stackoverflow.tag.service.TagService;
import com.group5.stackoverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final TagService tagService;

    public QuestionController(QuestionService questionService,
                              QuestionMapper questionMapper,
                              TagService tagService) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.tagService = tagService;
    }

    // 질문 생성
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostToQuestion(requestBody);

        Question createdQuestion = questionService.createQuestion(question);
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, createdQuestion.getQuestionId());

        return ResponseEntity.created(location).build();
    }

    // 질문 수정
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive Long questionId,
                                        @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);

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
    public ResponseEntity getQuestions(
            @PageableDefault(sort = "question-id", direction = Sort.Direction.DESC)
                                           Pageable pageable) {
        Page<Question> pageQuestions = questionService.findQuestions(pageable);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(questionMapper.questionsToQuestionResponses(questions), pageQuestions),
                HttpStatus.OK);
    }

    // 질문에 대한 태그 조회
    @GetMapping("/tags")
    public ResponseEntity getQuestionByTag(@RequestParam String tagName,
                                           @PageableDefault(sort = "question-id", direction = Sort.Direction.DESC)
                                           Pageable pageable) {
        return null;
    }

    // 검색에 의한 질문 조회
    @GetMapping("/search")
    public ResponseEntity searchQuestion(@RequestParam String search,
                                         @PageableDefault(sort = "question-id", direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        Page<Question> searchQuestionPage = questionService.searchQuestion(search, pageable);
        List<Question> searchQuestionList = searchQuestionPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        questionMapper.questionsToQuestionResponses(searchQuestionList), searchQuestionPage),
                HttpStatus.OK);
    }

    // 질문 삭제
    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive Long questionId) {
        questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
