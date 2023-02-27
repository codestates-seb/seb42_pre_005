package com.group5.stackoverflow.question.entity;


import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.audit.Auditable;
import com.group5.stackoverflow.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int voteCount;

    @Column(nullable = false)
    private int views;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addQuestionTag(QuestionTag questionTag) {
        this.questionTags.add(questionTag);
        if (questionTag.getQuestion() != this) {
            questionTag.setQuestion(this);
        }
    }
}
