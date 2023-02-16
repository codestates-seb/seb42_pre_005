package com.group5.stackoverflow.answer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Answer {

    @Id
    private Long answerId;
    private String content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Answer(String content) {
        this.content = content;
    }
}
