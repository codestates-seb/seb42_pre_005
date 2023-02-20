package com.group5.stackoverflow.answer.entity;

import com.group5.stackoverflow.audit.Auditable;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Answer(String content) {
        this.content = content;
    }
}
