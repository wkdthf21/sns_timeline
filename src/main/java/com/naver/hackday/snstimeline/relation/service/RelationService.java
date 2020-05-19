package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.BadRequestException;
import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.relation.domain.RelationRepository;
import com.naver.hackday.snstimeline.relation.controller.dto.RelationUserResponseDto;
import com.naver.hackday.snstimeline.timeline.service.TimelineService;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import com.naver.hackday.snstimeline.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RelationService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final TimelineService timelineService;

    @Transactional
    public void follow(String userId, String followingId) {
        if (userId.equals(followingId)) {
            throw new BadRequestException("followingUser-id", "스스로를 구독할 수 없습니다.");
        }

        User user = getUserEntity(userId, "user-id");
        User followingUser = getUserEntity(followingId, "followingUser-id");

        if (relationRepository.findByUserAndFollowingUser(user, followingUser).isPresent()) {
            throw new BadRequestException("followingUser-id", "이미 구독중인 친구입니다.");
        }

        Relation relation = relationRepository.save(Relation.builder()
                .user(user)
                .followingUser(followingUser)
                .build());

        timelineService.addTimeline(relation);
    }

    @Transactional(readOnly = true)
    public List<RelationUserResponseDto> getFollowings(String userId) {
        User user = getUserEntity(userId, "user-id");

        return relationRepository.findByUser(user)
                .stream()
                .map(relation -> new RelationUserResponseDto(relation.getFollowingUser(), isFollower(user, relation.getFollowingUser()), true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RelationUserResponseDto> getFollowers(String userId) {
        User user = getUserEntity(userId, "user-id");

        return relationRepository.findByFollowingUser(user)
                .stream()
                .map(relation -> new RelationUserResponseDto(relation.getUser(), true, isFollowing(user, relation.getUser())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelFollow(String userId, String followingId) {
        User user = getUserEntity(userId, "user-id");
        User followingUser = getUserEntity(followingId, "followingUser-id");

        Relation relationToCancel = relationRepository.findByUserAndFollowingUser(user, followingUser)
                .orElseThrow(() -> new BadRequestException("followingUser-id", "구독중인 친구가 아닙니다."));

        timelineService.deleteTimeline(relationToCancel);
        relationRepository.delete(relationToCancel);
    }

    private User getUserEntity(String userId, String field) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }

    private Boolean isFollower(User user, User followingUser) {
        return relationRepository.findByUserAndFollowingUser(followingUser, user).isPresent();
    }

    private Boolean isFollowing(User user, User followerUser) {
        return relationRepository.findByUserAndFollowingUser(user, followerUser).isPresent();
    }
}
