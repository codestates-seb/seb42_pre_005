package com.group5.stackoverflow.answer.service;

import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import com.group5.stackoverflow.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public AnswerService(AnswerRepository answerRepository,
                         QuestionService questionService, QuestionRepository questionRepository,
                         MemberService memberService, MemberRepository memberRepository) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    public Answer createAnswer(Answer answer, Long questionId) {
        Question question = questionService.findVerifiedQuestion(questionId);
        // question 엔티티에 코드 추가
//        question.setAnswers(answer);
        // memberService 회원 검증 코드 추가
        // 답변 갯수 추가 해야함 (Question 엔티티 answerCount 추가)
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Long answerId, Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);
    }

    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        Answer answer = findAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUNT));

        return answer;
    }
}
