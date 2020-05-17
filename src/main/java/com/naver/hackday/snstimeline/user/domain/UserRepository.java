package com.naver.hackday.snstimeline.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findByUserIdContainingOrNicknameContaining(String userId, String nickname);
	  Optional<User> findByUserId(String userId);
}
