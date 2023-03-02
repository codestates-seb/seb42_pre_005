package com.group5.stackoverflow.answer.service;

import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
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

    // 답변 등록
    public Answer createAnswer(Answer answer, Long questionId) {
        Question question = questionService.findVerifiedQuestion(questionId);
        answer.setQuestion(question);
        Member member = memberService.findVerifiedMember(answer.getMember().getMemberId());
        answer.setMember(member);
        answer.setCreatedAt(LocalDateTime.now());

        return answerRepository.save(answer);
    }

    // 답변 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Answer updateAnswer(Answer answer, Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));
        findAnswer.setModifiedAt(LocalDateTime.now());

        return answerRepository.save(findAnswer);
    }

    // 답변 삭제
    public void deleteAnswer(Long answerId, String email) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        if (findAnswer.getMember() != memberService.findMemberByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
        }
        answerRepository.delete(findAnswer);
    }

    // 추천 로직
    public Answer updateVote(Long answerId, String updown) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        int vote = (updown.equals("up")) ? findAnswer.getVoteCount() + 1 : findAnswer.getVoteCount() - 1;

        findAnswer.setVoteCount(vote);
        return answerRepository.save(findAnswer);
    }

    // 답변 검증
    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        Answer answer = findAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return answer;
    }

    // 내가 쓴 답변 조회
    public Page<Answer> findMyAnswers(Long memberId, int page, int size) {
        return answerRepository.findAllByMemberMemberId(PageRequest.of(page, size, Sort.by("answerId").descending()), memberId);
    }

}
