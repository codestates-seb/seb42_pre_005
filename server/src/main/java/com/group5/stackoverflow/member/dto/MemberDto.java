package com.group5.stackoverflow.member.dto;

import com.group5.stackoverflow.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


public class MemberDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotNull
        String name;

        @Email
        String email;

        @NotNull
        String password;

        @Nullable
        int age;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        Long memberId;

        @Nullable
        String name;

//        @Nullable
//        String password;

        @Nullable
        int age;

        int voteCount;


        @Nullable
        Member.MemberStatus memberStatus;


        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        Long memberId;

        @NotNull
        String name;

        @Email
        String email;


        @Nullable
        int age;

        int voteCount;

        Member.MemberStatus memberStatus;

        public String getMemberStatus() {
            return memberStatus.getStatus();
        }

    }
}
