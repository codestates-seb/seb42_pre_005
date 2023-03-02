package com.group5.stackoverflow.question.entity;

import com.group5.stackoverflow.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public QuestionTag(Question question, Tag tag) {
        addQuestion(question);
        addTag(tag);
    }

    public void addQuestion(Question question) {
        this.question = question;
        question.setQuestionTag(this);
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        int count = tag.getQuestionCount() + 1;
        tag.setQuestionCount(count);
        tag.setQuestionTag(this);
    }

}
