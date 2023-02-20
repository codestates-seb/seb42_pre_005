package com.group5.stackoverflow.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    ANSWER_NOT_FOUND(404,"Answer not found"),
    QUESTION_NOT_FOUND(404,"Question not found"),
    REQUEST_FORBIDDEN(403,"Request forbidden"),
    COMMENT_NOT_FOUND(409, "Tag exists");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
