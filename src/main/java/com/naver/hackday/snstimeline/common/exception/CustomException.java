package com.naver.hackday.snstimeline.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String field;

    public CustomException(String field, String message) {
        super(message);
        this.field = field;
    }
}
