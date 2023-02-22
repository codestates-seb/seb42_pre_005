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
    private final MemberService memberService;

    public AnswerService(AnswerRepository answerRepository,
                         QuestionService questionService,
                         MemberService memberService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.memberService = memberService;
    }

    // 답변 생성
    public Answer createAnswer(Long questionId, Answer answer) {
        // member, question 추가함.
        Question question = questionService.findVerifiedQuestion(questionId);
        answer.setQuestion(question);
        Member member = memberService.findVerifiedMember(answer.getMember().getMemberId());
        answer.setMember(member);

        return answerRepository.save(answer);
    }

    // 답변 수정
    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));
        findAnswer.setModifiedAt(LocalDateTime.now());

        return answerRepository.save(findAnswer);
    }

    // 답변 삭제
    public void deleteAnswer(Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);

    }

    // 답변 검증
    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        Answer answer = findAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUNT));

        return answer;
    }

}
