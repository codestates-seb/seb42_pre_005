package com.group5.stackoverflow.answer.entity;

import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public Answer(String content) {
        this.content = content;
    }
}
