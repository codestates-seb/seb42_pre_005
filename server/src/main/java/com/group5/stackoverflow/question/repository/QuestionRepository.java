package com.group5.stackoverflow.question.repository;

import com.group5.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTitleContainingOrTextContaining(String title, String content, Pageable pageable);
}
