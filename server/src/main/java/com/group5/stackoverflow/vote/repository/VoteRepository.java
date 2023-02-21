package com.group5.stackoverflow.vote.repository;

import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByMemberAndQuestion(Member member, Question question);
    List<Vote> findAllByQuestion(Question question);
    List<Vote> findAllByMemberAndAnswer(Member member, Answer answer);
    List<Vote> findAllByAnswer(Answer answer);
}
