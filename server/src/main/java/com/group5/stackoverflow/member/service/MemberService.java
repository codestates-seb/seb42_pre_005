package com.group5.stackoverflow.member.service;

import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        return repository.save(member);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question) {
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));

        return repository.save(findQuestion);
    }

    @Transactional(readOnly = true)
    public Question findQuestion(Long questionId) {
        return findVerifiedQuestion(questionId);
    }

    public Page<Question> findQuestions(int page, int size) {
        return repository.findAll(PageRequest.of(
                page, size, Sort.by("questionId").descending()));
    }

    public void deleteQuestion(Long questionId) {
        Question findQuestion = findQuestion(questionId);

        repository.delete(findQuestion);
    }

    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(Long questionId) {
        Optional<Question> optionalQuestion = repository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() -> new RuntimeException());
        return findQuestion;
    }

    private void verifyExistsTitle(String title) {
        Optional<Question> question = repository.findByTitle(title);
        if (question.isPresent())
            throw new RuntimeException();
    }
}



}
