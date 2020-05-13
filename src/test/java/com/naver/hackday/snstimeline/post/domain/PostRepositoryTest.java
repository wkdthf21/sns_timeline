package com.naver.hackday.snstimeline.post.domain;

import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp(){

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

    }

    @Test
    public void Post저장성공테스트(){

        // when
        User user = userRepository.findAll().get(0);
        String userId = user.getUserId();

        Post post = postRepository.findAll().get(0);

        // then
        assertThat(userId).isEqualTo("user1");
        assertThat(post.getContents()).isEqualTo("contents1");
        assertThat(post.getUser().getUserId()).isEqualTo("user1");

    }

    @Test
    public void User와Post관계Test(){

        // when
        User user = userRepository.findAll().get(0);
        String userId = user.getUserId();

        List<Post> postList = user.getPostList();

        // then
        assertThat(userId).isEqualTo("user1");

        for(Post p : postList){
            assertThat(p.getContents()).isEqualTo("contents1");
        }

    }
}