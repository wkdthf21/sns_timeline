package com.naver.hackday.snstimeline.user.service;

import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.user.controller.dto.UserResponseDto;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void 친구찾기_결과있음() {
        //given
        String searchKeyword = "red";
        List<User> givenResult = new ArrayList<>();

        givenResult.add(User.builder()
                .userId("redapple")
                .build());
        givenResult.add(User.builder()
                .userId("redcherry")
                .build());

        given(userRepository.findByUserIdContainingOrNicknameContaining(searchKeyword, searchKeyword)).willReturn(givenResult);

        //when
        List<UserResponseDto> searchResult = userService.searchUser(searchKeyword);

        //then
        assertThat(searchResult.size()).isEqualTo(2);
    }

    @Test(expected = NotFoundException.class)
    public void 친구찾기_결과없음() {
        //given
        String searchKeyword = "blue";

        given(userRepository.findByUserIdContainingOrNicknameContaining(searchKeyword, searchKeyword)).willReturn(Collections.emptyList());

        //when
        userService.searchUser(searchKeyword);
    }
}