package com.group5.stackoverflow.tag.entity;

import com.group5.stackoverflow.question.entity.QuestionTag;
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
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    // 동일한 태그가 존재하면 안되기 때문에 unique 지정
    @Column(unique = true, nullable = false, length = 35)
    private String tagName;

    private Integer questionCount = 0;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags = new ArrayList<>();


    public void setQuestionTags(QuestionTag questionTag) {
        this.questionTags.add(questionTag);
        if (questionTag.getTag() != this) {
            questionTag.setTag(this);
        }
    }

    public void calQuestionCount() {
        this.questionCount = questionTags.size();
    }
}
