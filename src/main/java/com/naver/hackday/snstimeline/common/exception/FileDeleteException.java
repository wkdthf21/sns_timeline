package com.naver.hackday.snstimeline.common.exception;

import lombok.Getter;

@Getter
public class FileDeleteException extends RuntimeException {

	private String field;

	public FileDeleteException(String field, String message){
		super(message);
		this.field = field;
	}

}
