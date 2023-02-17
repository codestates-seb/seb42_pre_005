package com.group5.stackoverflow.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    Long memberId;

    @NotNull
    String name;


    @Nullable
    int age;


    @Email
    String email;


    int voteCount;

    @NotNull
    String password;

    // 스테이터스
    MemberStatus memberStatus;

    // 크리에이티드

    // 모디파이드


    public enum MemberStatus{

        MEMBER_NEW("신규 멤버"),
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

}
