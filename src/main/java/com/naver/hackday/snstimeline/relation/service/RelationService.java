package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.user.controller.dto.UserResponseDto;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RelationService {

    private final UserRepository userRepository;

    @Transactional
    public void follow(Long id, Long followingId) {
        User user = getUserEntity(id, "id");
        User followingUser = getUserEntity(followingId, "following-id");

        user.addFollowing(followingUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getFollowings(Long id) {
        User user = getUserEntity(id, "id");

        return user.getFollowings()
                .stream()
                .map(u -> new UserResponseDto(u, user.isFollower(u), true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getFollowers(Long id) {
        User user = getUserEntity(id, "id");

        return user.getFollowers()
                .stream()
                .map(u -> new UserResponseDto(u, true, user.isFollowing(u)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelFollow(Long id, Long followingId) {
        User user = getUserEntity(id, "id");
        User followingUser = getUserEntity(followingId, "following-id");

        user.cancelFollowing(followingUser);
    }

    private User getUserEntity(Long id, String field) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }
}
