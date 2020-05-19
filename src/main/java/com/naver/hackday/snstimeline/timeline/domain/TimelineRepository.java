package com.naver.hackday.snstimeline.timeline.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
}
