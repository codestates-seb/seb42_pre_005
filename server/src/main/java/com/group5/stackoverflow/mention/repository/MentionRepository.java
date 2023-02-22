package com.group5.stackoverflow.mention.repository;

import com.group5.stackoverflow.mention.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Long> {
}
