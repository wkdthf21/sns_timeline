package com.naver.hackday.snstimeline.post.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.naver.hackday.snstimeline.config.FileUploadConfig;

public class FileServiceTest {
	
	private FileUploadConfig fileUploadProperty;
	private FileService fileService;
	private String filename;
	private ClassPathResource cpr;
	private MultipartFile multipartFile;
	
	
	@Before
	public void before() {
		
		fileUploadProperty = new FileUploadConfig();
		fileUploadProperty.setDirectory("./uploads");
		fileService = new FileService(fileUploadProperty);
	}
	
	@After
	public void after() {
		fileService.deleteFile(filename);
	}
	
	@Test
	public void saveFile() throws Exception {
			
		// given
		cpr = new ClassPathResource("test_img.png");
		multipartFile = new MockMultipartFile( cpr.getPath(), "test_img.png", null,
			cpr.getInputStream());

		// when
		filename = fileService.saveFile(multipartFile);

		// then
		assertThat(filename).isNotNull();
	}

	@Test
	public void downloadFileAsResource() throws IOException {
		// given
		cpr = new ClassPathResource("test_img.png");
		multipartFile = new MockMultipartFile( cpr.getPath(), "test_img.png", null,
			cpr.getInputStream());
		filename = fileService.saveFile(multipartFile);
		
		// when
		Resource resource = fileService.downloadFileAsResource(filename);
		// then
		assertThat(resource).isNotNull();
		assertThat(resource.exists()).isTrue();
		assertThat(resource.getFilename()).contains(filename);
	}


}