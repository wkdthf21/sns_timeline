package com.naver.hackday.snstimeline.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

	private String field;

	public NotFoundException(String field, String message) {
		super(message);
		this.field = field;
	}
}