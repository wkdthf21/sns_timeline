package com.naver.hackday.snstimeline.common.exception;

import lombok.Getter;

@Getter
public class NoContentException extends RuntimeException {

    private String field;

    public NoContentException(String field, String message) {
        super(message);
        this.field = field;
    }
}
