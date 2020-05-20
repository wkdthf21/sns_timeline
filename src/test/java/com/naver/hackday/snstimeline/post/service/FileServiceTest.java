package com.naver.hackday.snstimeline.post.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

	@Autowired
	private FileService fileService;
	private String savedFileName;

	@After
	public void tearDown() throws Exception {
	}

	@Before
	public void saveFile() throws Exception {

		// given
		ClassPathResource cpr = new ClassPathResource("test_img.png");

		MultipartFile multipartFile = new MockMultipartFile( cpr.getPath(), "test_img.png", null,
			cpr.getInputStream());

		// when
		savedFileName = fileService.saveFile(multipartFile);

		// then
		assertThat(savedFileName).isNotNull();

	}

	@Test
	public void downloadFileAsResource() {
		// when
		Resource resource = fileService.downloadFileAsResource(savedFileName);
		// then
		assertThat(resource).isNotNull();
		assertThat(resource.exists()).isTrue();
		assertThat(resource.getFilename()).contains(savedFileName);
	}


}