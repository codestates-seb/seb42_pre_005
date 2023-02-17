package com.group5.stackoverflow.member.entity;

import com.group5.stackoverflow.mention.entity.Mention;

import javax.persistence.*;

@Entity
public class MemberMention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberMentionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentionId")
    private Mention mention;
}
