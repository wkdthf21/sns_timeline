package com.naver.hackday.snstimeline.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix="file")
/* 파일 업로드 경로 기본값 설정 */
public class FileUploadConfig {
	private String directory;
}
