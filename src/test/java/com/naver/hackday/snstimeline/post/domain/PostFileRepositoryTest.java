package com.naver.hackday.snstimeline.post.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostFileRepositoryTest {

	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostFileRepository postFileRepository;

	@Before
	public void setUp() {

		// given
		User savedUser = User.builder()
			.userId("user1")
			.build();

		userRepository.save(savedUser);

		Post savedPost = Post.builder()
			.user(savedUser)
			.contents("contents1")
			.build();

		postRepository.save(savedPost);

		PostFile savedPostFile = PostFile.builder()
					.post(savedPost)
					.url("test-url")
					.build();

		postFileRepository.save(savedPostFile);

	}

	@Test
	public void PostFile저장성공테스트(){

		// when
		User user = userRepository.findAll().get(0);
		String userId = user.getUserId();

		Post post = postRepository.findAll().get(0);

		PostFile postFile = postFileRepository.findAll().get(0);

		// then
		assertThat(userId).isEqualTo("user1");

		assertThat(post.getContents()).isEqualTo("contents1");
		assertThat(post.getUser().getUserId()).isEqualTo("user1");
		assertThat(post.getPostFileList().get(0).getUrl()).isEqualTo("test-url");

		assertThat(postFile.getUrl()).isEqualTo("test-url");
		assertThat(postFile.getPost()).isEqualTo(post);

	}



}