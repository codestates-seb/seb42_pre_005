package com.group5.stackoverflow.tag.entity;

import com.group5.stackoverflow.question.entity.QuestionTag;
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
@NoArgsConstructor
public class Tag {

    @Id
    private Long tagId;
    private String tagName;

    private int askedTotal;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags = new ArrayList<>();

}
