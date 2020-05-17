package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.BadRequestException;
import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.relation.domain.RelationRepository;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RelationServiceTest {

    private RelationService relationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RelationRepository relationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        relationService = new RelationService(userRepository, relationRepository);
    }

    @Test
    public void 구독_성공() {
        //given
        User user = new User();
        User followingUser = new User();

        given(userRepository.findById("test1")).willReturn(Optional.of(user));
        given(userRepository.findById("test2")).willReturn(Optional.of(followingUser));

        //when
        relationService.follow("test1", "test2");

        //then
        verify(relationRepository).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void 존재하지않는유저_구독() {
        //given
        User user = new User();

        given(userRepository.findById("test1")).willReturn(Optional.of(user));
        given(userRepository.findById("test2")).willReturn(Optional.empty());

        //when
        relationService.follow("test1", "test2");
    }

    @Test(expected = BadRequestException.class)
    public void 스스로_구독() {
        //given
        String id = "test";

        //when
        relationService.follow(id, id);
    }

    @Test(expected = BadRequestException.class)
    public void 구독중인친구_구독신청() {
        //given
        User user = new User();
        User followingUser = new User();
        Relation relation = new Relation();

        given(userRepository.findById("test1")).willReturn(Optional.of(user));
        given(userRepository.findById("test2")).willReturn(Optional.of(followingUser));
        given(relationRepository.findByUserAndFollowingUser(user, followingUser)).willReturn(Optional.of(relation));

        //when
        relationService.follow("test1", "test2");
    }

    @Test
    public void 구독취소_성공() {
        //given
        User user = new User();
        User followingUser = new User();
        Relation relation = new Relation();

        given(userRepository.findById("test1")).willReturn(Optional.of(user));
        given(userRepository.findById("test2")).willReturn(Optional.of(followingUser));
        given(relationRepository.findByUserAndFollowingUser(user, followingUser)).willReturn(Optional.of(relation));

        //when
        relationService.cancelFollow("test1", "test2");

        //then
        verify(relationRepository).delete(any());
    }

    @Test(expected = BadRequestException.class)
    public void 구독중이아닌친구_구독취소() {
        //given
        User user = new User();
        User noFollowingUser = new User();

        given(userRepository.findById("test1")).willReturn(Optional.of(user));
        given(userRepository.findById("test2")).willReturn(Optional.of(noFollowingUser));
        given(relationRepository.findByUserAndFollowingUser(user, noFollowingUser)).willReturn(Optional.empty());

        //when
        relationService.cancelFollow("test1", "test2");
    }
}