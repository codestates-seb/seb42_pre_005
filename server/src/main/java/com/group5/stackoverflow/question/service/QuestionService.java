package com.group5.stackoverflow.question.service;

import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Question createQuestion(Question question) {
        memberService.findMember(question.getMember().getMemberId());

        return repository.save(question);
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
        Question findQuestion = findVerifiedQuestion(questionId);

        int findViews = findQuestion.getViews() + 1;
        findQuestion.setViews(findViews);
        repository.save(findQuestion);

        return findQuestion;
    }

    public Page<Question> findQuestions(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Question> searchQuestion(String search, Pageable pageable) {
        Page<Question> questionPage = repository.findByTitleContainingOrTextContaining(search, search, pageable);

        return questionPage;
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

}
