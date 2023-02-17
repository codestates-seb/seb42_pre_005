package com.group5.stackoverflow.member.dto;

import com.group5.stackoverflow.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


public class MemberDto {
    @Getter
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
    public static class Patch{
        @Nullable
        String name;

        @Nullable
        String password;

        @Nullable
        int age;

    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response{
        Long memberId;

        @NotNull
        String name;

        @Email
        String email;

        @NotNull
        String password;

        @Nullable
        int age;

        int voteCount;

        Member.MemberStatus memberStatus;

    }
}
