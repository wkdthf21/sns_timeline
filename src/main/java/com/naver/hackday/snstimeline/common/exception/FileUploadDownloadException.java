package com.naver.hackday.snstimeline.common.exception;

import lombok.Getter;

@Getter
public class FileUploadDownloadException extends RuntimeException {

	private String field;

	public FileUploadDownloadException(String field, String message){
		super(message);
		this.field = field;
	}

}
