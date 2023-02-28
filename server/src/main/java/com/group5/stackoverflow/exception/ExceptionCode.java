package com.group5.stackoverflow.exception;

import lombok.Getter;

// ??
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    REQUEST_FORBIDDEN(403,"Request forbidden"),
    MEMBER_UNAUTHORIZED(401, "UNAUTHORIZED"),
    ACCESS_DENIED(401, "Access Denied");

    


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
