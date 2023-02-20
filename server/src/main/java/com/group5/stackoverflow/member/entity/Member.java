package com.group5.stackoverflow.member.entity;

import com.group5.stackoverflow.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;

    @Column(nullable = false, length = 50)
    String name;

    @Column(nullable = true)
    Integer age;

    @Column(nullable = false, updatable = false, unique = true)
    String email;

    @Column(nullable = false)
    int voteCount;

    @Column(nullable = false)
    String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    MemberStatus memberStatus = MemberStatus.MEMBER_NEW;

    // 연관관계 매핑
    @OneToMany(mappedBy = "member")
    @Column(name = "MEMBER_MENTION")
    List<MemberMention> memberMentions = new ArrayList<>();

    // TODO Answer 연관관계 매핑
    // TODO comment 연관관계 매핑



    public enum MemberStatus{

        MEMBER_NEW("MEMBER_NEW"),
        MEMBER_ACTIVE("MEMBER_ACTIVE"),
        MEMBER_SLEEP("MEMBER_SLEEP"),
        MEMBER_QUIT("MEMBER_QUIT");

        @Getter
        String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

}
