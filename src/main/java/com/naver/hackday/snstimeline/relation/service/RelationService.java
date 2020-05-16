package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.BadRequestException;
import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.relation.domain.RelationRepository;
import com.naver.hackday.snstimeline.relation.controller.dto.RelationUserResponseDto;
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
    private final RelationRepository relationRepository;

    @Transactional
    public void follow(Long id, Long followingId) {
        if (id.equals(followingId)) {
            throw new BadRequestException("followingUser-id", "스스로를 구독할 수 없습니다.");
        }

        User user = getUserEntity(id, "id");
        User followingUser = getUserEntity(followingId, "followingUser-id");

        if (relationRepository.findByUserAndFollowingUser(user, followingUser).isPresent()) {
            throw new BadRequestException("followingUser-id", "이미 구독중인 친구입니다.");
        }

        relationRepository.save(Relation.builder()
                .user(user)
                .followingUser(followingUser)
                .build());
    }

    @Transactional(readOnly = true)
    public List<RelationUserResponseDto> getFollowings(Long id) {
        User user = getUserEntity(id, "id");

        return relationRepository.findByUser(user)
                .stream()
                .map(relation -> new RelationUserResponseDto(relation.getFollowingUser(), isFollower(user, relation.getFollowingUser()), true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RelationUserResponseDto> getFollowers(Long id) {
        User user = getUserEntity(id, "id");

        return relationRepository.findByFollowingUser(user)
                .stream()
                .map(relation -> new RelationUserResponseDto(relation.getUser(), true, isFollowing(user, relation.getUser())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelFollow(Long id, Long followingId) {
        User user = getUserEntity(id, "id");
        User followingUser = getUserEntity(followingId, "followingUser-id");

        Relation relationToCancel = relationRepository.findByUserAndFollowingUser(user, followingUser)
                .orElseThrow(() -> new BadRequestException("followingUser-id", "구독중인 친구가 아닙니다."));

        relationRepository.delete(relationToCancel);
    }

    private User getUserEntity(Long id, String field) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }

    private Boolean isFollower(User user, User followingUser) {
        return relationRepository.findByUserAndFollowingUser(followingUser, user).isPresent();
    }

    private Boolean isFollowing(User user, User followerUser) {
        return relationRepository.findByUserAndFollowingUser(user, followerUser).isPresent();
    }
}
