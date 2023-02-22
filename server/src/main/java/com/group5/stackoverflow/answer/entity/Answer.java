package com.group5.stackoverflow.answer.entity;

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
    private Long answerId;
    private String content;
    private int voteCount;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public Answer(String content) {
        this.content = content;
    }
}
