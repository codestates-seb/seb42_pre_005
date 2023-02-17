package com.group5.stackoverflow.answer.service;

import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.question.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));
        findAnswer.setModifiedAt(LocalDateTime.now());

        return answerRepository.save(findAnswer);
    }

    public Answer recommendAnswer() {
        return null;
    }

    public void deleteAnswer(Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(findAnswer);
    }

    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        Answer answer = findAnswer.orElseThrow(() -> new RuntimeException("등록된 답변이 없습니다."));

        return answer;
    }
}
