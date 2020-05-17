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
        assertThat(postList.size()).isEqualTo(1);

        for(Post p : postList){
            assertThat(p.getContents()).isEqualTo("contents1");
        }

    }

    @Test
    public void Post내용수정테스트(){

        // given
        User user = userRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);

        post.modifyContents("contents2");
        postRepository.updateContents(post);

        // when
        Post savedPost = postRepository.findAll().get(0);

        // then
        assertThat(user.getUserId()).isEqualTo("user1");
        assertThat(savedPost.getContents()).isEqualTo("contents2");
        assertThat(savedPost.getUser().getUserId()).isEqualTo("user1");

        // when
        List<Post> postList = user.getPostList();

        // then
        assertThat(postList.size()).isEqualTo(1);

        for(Post p : postList){
            assertThat(p.getContents()).isEqualTo("contents2");
        }

    }

    @Test
    public void Post삭제테스트(){

        // given
        Post post = postRepository.findAll().get(0);
        postRepository.deletePostById(post);

        //when
        List<Post> postList = postRepository.findAll();

        User user = userRepository.findAll().get(0);
        List<Post> userPostList = user.getPostList();

        // then
        assertThat(postList.size()).isZero();
        assertThat(userPostList.size()).isZero();

    }

}