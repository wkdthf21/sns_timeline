package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.CustomException;
import com.naver.hackday.snstimeline.relation.controller.dto.FollowResponseDto;
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
    public List<FollowResponseDto> getFollowings(Long id) {
        User user = getUserEntity(id, "id");

        return user.getFollowings()
                .stream()
                .map(FollowResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFollowers(Long id) {
        User user = getUserEntity(id, "id");

        return user.getFollowers()
                .stream()
                .map(FollowResponseDto::new)
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
                .orElseThrow(() -> new CustomException(field, "존재하지 않는 유저입니다."));
    }
}
