package com.group5.stackoverflow.member.repository;

import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
