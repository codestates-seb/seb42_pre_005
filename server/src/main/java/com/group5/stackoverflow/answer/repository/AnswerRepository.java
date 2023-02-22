package com.group5.stackoverflow.answer.repository;

import com.group5.stackoverflow.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository <Answer, Long> {

    Optional<Answer> findByAnswerId(Long answerId);
}
