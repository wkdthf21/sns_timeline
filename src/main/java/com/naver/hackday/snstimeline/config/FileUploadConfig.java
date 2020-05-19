package com.naver.hackday.snstimeline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
/* 파일 업로드 경로 기본값 설정 */
public class FileUploadConfig {
	@Value("${file.directory}")
	private String uploadDirectory;
}
