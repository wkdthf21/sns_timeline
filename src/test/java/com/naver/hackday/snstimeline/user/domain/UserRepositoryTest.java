package com.naver.hackday.snstimeline.user.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void User_생성() {
        //given
        LocalDateTime now = LocalDateTime.now();
        userRepository.save(User.builder()
                .userId("test")
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);

        assertThat(user.getUserId()).isEqualTo("test");
        assertThat(user.getCreatedDate()).isAfter(now);
        assertThat(user.getModifiedDate()).isAfter(now);
    }
}