package com.group5.stackoverflow.member.repository;

import com.group5.stackoverflow.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
