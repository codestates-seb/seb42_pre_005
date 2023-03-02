package com.group5.stackoverflow.answer.controller;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.utils.Checker;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.utils.UriCreator;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping
@CrossOrigin(value = {"http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com",
        "http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com/"})
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper mapper;
    private final MemberService memberService;

    private final JwtTokenizer jwtTokenizer;

    public AnswerController(AnswerService answerService, AnswerMapper mapper,
                            MemberService memberService, JwtTokenizer jwtTokenizer) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.memberService = memberService;
        this.jwtTokenizer = jwtTokenizer;
    }

    // 답변 등록
    @PostMapping("/{member-id}/questions/{question-id}/answers")
    public ResponseEntity postAnswer(@PathVariable("member-id") @Positive Long memberId,
            @PathVariable("question-id") @Positive Long questionId,
                                     @Valid @RequestBody AnswerDto.Post requestBody){

        requestBody.addMemberId(memberId);
        requestBody.addQuestionId(questionId);

//        // 헤더에 담겨서 넘어온 JWT토큰을 해독하여 email 정보를 가져옴
//        String jwtEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (memberService.findMemberByEmail(jwtEmail).getMemberId() != requestBody.getMemberId()) {
//            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
//        }

        Answer answer = mapper.answerPostDtoToAnswer(requestBody);
        Answer response = answerService.createAnswer(answer, questionId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.CREATED);
    }

    // 답변 수정
    @PatchMapping("/{member-id}/answers/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("member-id") @Positive Long memberId,
                                      @PathVariable("answer-id") @Positive Long answerId,
                                      @Valid @RequestBody AnswerDto.Patch requestBody) {
        requestBody.addMemberId(memberId);
        requestBody.addAnswerId(answerId);

        // 헤더에 담겨서 넘어온 JWT토큰을 해독하여 email 정보를 가져옴
        String jwtEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (memberService.findMemberByEmail(jwtEmail).getMemberId() != requestBody.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
        }

        Answer response = answerService.updateAnswer(mapper.answerPatchDtoToAnswer(requestBody), answerId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(response)), HttpStatus.OK);
    }

    // 추천수 updown 기능
    @PatchMapping("/answers/{answer-id}/vote")
    public ResponseEntity patchAnswerVote(@PathVariable("answer-id") @Positive Long answerId,
                                          @RequestParam String updown,
                                          @Valid @RequestBody AnswerDto.PatchVote requestBody ) {
        requestBody.addAnswerId(answerId);
        Answer answer = answerService.updateVote(requestBody.getAnswerId(), updown);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.answerToAnswerResponse(answer)), HttpStatus.OK);
    }

    // 답변 삭제
    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive Long answerId) {
        // 헤더에 담겨서 넘어온 JWT토큰을 해독하여 email 정보를 가져옴
        String jwtEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        answerService.deleteAnswer(answerId, jwtEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 내가 쓴 답변 조회
    @GetMapping("/answers/my")
    public ResponseEntity getMyAnswers(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         HttpServletRequest request) {
        Page<Answer> pageAnswers = answerService.findMyAnswers(Checker.getMemberId(), page - 1, size);
        List<Answer> answers = pageAnswers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponses(answers), pageAnswers),
                HttpStatus.OK);
    }
}
