package com.naver.hackday.snstimeline.timeline.service;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.relation.domain.RelationRepository;
import com.naver.hackday.snstimeline.timeline.domain.Timeline;
import com.naver.hackday.snstimeline.timeline.domain.TimelineRepository;
import com.naver.hackday.snstimeline.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final RelationRepository relationRepository;

    public void addTimeline(Post post) {
        User writer = post.getUser();

        for (Relation r : relationRepository.findByFollowingUser(writer)) {
            timelineRepository.save((Timeline.builder()
                    .user(r.getUser())
                    .writer(writer)
                    .post(post).build()));
        }
    }
}
