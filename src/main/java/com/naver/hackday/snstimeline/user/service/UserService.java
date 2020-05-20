package com.naver.hackday.snstimeline.user.service;

import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.user.controller.dto.UserResponseDto;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUser(String userIdOrNickname) {
        List<UserResponseDto> searchResult = userRepository.findByUserIdContainingOrNicknameContaining(userIdOrNickname, userIdOrNickname)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

        if(searchResult.isEmpty()) {
            throw new NotFoundException("userIdOrNickname", "해당 키워드가 포함된 아이디나 닉네임을 가진 유저가 존재하지 않습니다.");
        }

        return searchResult;
    }
}
