package com.group5.stackoverflow.question.repository;

import com.group5.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByTitleContainingIgnoreCase(Pageable pageable, String keyword);
    Page<Question> findAllByMemberMemberId(Pageable pageable, Long memberId);
}
