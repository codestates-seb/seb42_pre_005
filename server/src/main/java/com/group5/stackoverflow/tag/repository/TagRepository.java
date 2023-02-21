package com.group5.stackoverflow.tag.repository;

import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    Page<Tag> findAllByTagNameContains(String tagName, Pageable pageable);

//    Page<Tag> findByOrderByAskedTotal(Pageable pageable);
//    Page<Tag> findByOrderByTagNameAsc(Pageable pageable);
//    Page<Tag> findByOrderByCreatedAtDesc(Pageable pageable);


}
