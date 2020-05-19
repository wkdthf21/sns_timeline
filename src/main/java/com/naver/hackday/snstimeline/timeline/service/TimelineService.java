package com.naver.hackday.snstimeline.timeline.service;

import com.naver.hackday.snstimeline.common.exception.NoContentException;
import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.relation.domain.RelationRepository;
import com.naver.hackday.snstimeline.timeline.controller.dto.TimelineResponseDto;
import com.naver.hackday.snstimeline.timeline.domain.Timeline;
import com.naver.hackday.snstimeline.timeline.domain.TimelineRepository;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    public List<TimelineResponseDto> getTimeline(String userId) {
        User user = getUserEntity(userId, "user-id");
        List<Timeline> timelines = timelineRepository.findByUser(user);

        if (timelines.isEmpty()) {
            throw new NoContentException("user-id", "타임라인에 표시할 글이 없습니다.");
        }

        return timelines.stream()
                .map(TimelineResponseDto::new)
                .collect(Collectors.toList());
    }

    public void addTimeline(Post post) {
        User writer = post.getUser();

        for (Relation relation : relationRepository.findByFollowingUser(writer)) {
            timelineRepository.save((Timeline.builder()
                    .relation(relation)
                    .post(post)
                    .build()));
        }
    }

    public void addTimeline(Relation relation) {
        for (Post post : relation.getFollowingUser().getPostList()) {
            timelineRepository.save(Timeline.builder()
                    .relation(relation)
                    .post(post)
                    .build());
        }
    }

    private User getUserEntity(String userId, String field) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }
}
