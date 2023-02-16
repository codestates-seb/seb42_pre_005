package com.group5.stackoverflow.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class MemberDto {
    public class Post{
        @NotNull
        String name;

        @Email
        String email;

        @NotNull
        String password;

        @Nullable
        int age;

    }

    public class Patch{
        @Nullable
        String name;

        @Nullable
        String password;

        @Nullable
        int age;

    }

    public class Response{
        @NotNull
        String name;

        @Email
        String email;

        @NotNull
        String password;

        @Nullable
        int age;

    }
}
