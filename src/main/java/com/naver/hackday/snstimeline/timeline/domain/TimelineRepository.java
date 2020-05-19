package com.naver.hackday.snstimeline.timeline.domain;

import com.naver.hackday.snstimeline.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
    List<Timeline> findByUser(User user);
}
