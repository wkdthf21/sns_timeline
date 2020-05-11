package com.naver.hackday.snstimeline.relation.service;

import com.naver.hackday.snstimeline.common.exception.CustomException;
import com.naver.hackday.snstimeline.relation.controller.dto.FollowRequestDto;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RelationService {

    private final UserRepository userRepository;

    @Transactional
    public void follow(FollowRequestDto requestDto) {
        User fromUser = userRepository.findById(requestDto.getFromId())
                .orElseThrow(() -> new CustomException("fromId", "존재하지 않는 유저입니다."));
        User toUser = userRepository.findById(requestDto.getToId())
                .orElseThrow(() -> new CustomException("toId", "존재하지 않는 유저입니다."));

        fromUser.addFollowing(toUser);
        toUser.addFollower(fromUser);
    }
}
