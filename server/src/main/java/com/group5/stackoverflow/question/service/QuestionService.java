package com.group5.stackoverflow.question.service;

import com.group5.stackoverflow.question.entity.Question;
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

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Question createQuestion(Question question) {
        verifyExistsTitle(question.getTitle());

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
