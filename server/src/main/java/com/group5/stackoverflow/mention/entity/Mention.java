package com.group5.stackoverflow.mention.entity;

import com.group5.stackoverflow.member.entity.MemberMention;
import com.group5.stackoverflow.question.entity.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MentionId;

    @Column(nullable = false)
    boolean checked = false;

    @OneToMany(mappedBy = "mention", cascade = CascadeType.ALL)
    private List<MemberMention> memberMentions = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "questionId")
    private Question question;

    //TODO ANSWER 연결 필요함


}
