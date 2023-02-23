package com.group5.stackoverflow.question.service;

import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository repository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository repository,
                           MemberService memberService) {
        this.repository = repository;
        this.memberService = memberService;
    }

    // 질문 생성
    public Question createQuestion(Question question) {
        // 멤버가 맞는지 확인
        Member member = memberService.findMember(question.getMember().getMemberId());
//        question.setMember(member);

        return repository.save(question);
    }

    // 질문 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question) {
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));

        return repository.save(findQuestion);
    }

    // 질문 찾기(1개에 대한)
    @Transactional(readOnly = true)
    public Question findQuestion(Long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        // view 카운트 로직
        int findViews = findQuestion.getViews() + 1;
        findQuestion.setViews(findViews);
        repository.save(findQuestion);

        return findQuestion;
    }

    // 질문 전체 찾기
    public Page<Question> findQuestions(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size, Sort.by("question-id").descending()));
    }

    // 검색에 맞는 질문 찾기
//    public Page<Question> searchQuestion(String search, Pageable pageable) {
//        Page<Question> questionPage = repository.findByTitleContainingOrTextContaining(search, search, pageable);
//
//        return questionPage;
//    }

    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                repository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    // 질문 삭제
    public void deleteQuestion(Long questionId) {
        Question findQuestion = findQuestion(questionId);
        Member findMember = findQuestion.getMember();

        repository.delete(findQuestion);
    }
}
