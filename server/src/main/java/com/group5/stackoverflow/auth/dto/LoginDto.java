package com.group5.stackoverflow.auth.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;
    // TODO 인텔리제이가 못 찾는 현상
}
