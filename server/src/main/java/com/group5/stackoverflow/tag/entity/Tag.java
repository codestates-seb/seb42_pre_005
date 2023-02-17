package com.group5.stackoverflow.tag.entity;

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

//    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
//    private List<QuestionTag> questionTags = new ArrayList<>();

    public static Tag of(String string) {
        Tag tag = new Tag();
        tag.setTagName(string);
        return tag;
    }
}
