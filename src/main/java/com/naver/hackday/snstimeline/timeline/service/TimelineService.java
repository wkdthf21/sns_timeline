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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    private final CacheUpdateService cacheUpdateService;

    private final static Logger LOG = Logger.getGlobal();

    @Cacheable(value = "timelines", key = "#userId")
    public List<TimelineResponseDto> getTimeline(String userId) {

        LOG.info("======== Get Timeline Data Not Using Cache!!!!! ==========");
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
            String followerId = relation.getUser().getUserId();

            // save database
            Timeline timeline = timelineRepository.save(Timeline.builder()
                .relation(relation)
                .post(post)
                .build());

            //update cache
            List<TimelineResponseDto> timelineResponseDtoList = getTimeline(followerId);
            cacheUpdateService.updateAddCache(followerId, timelineResponseDtoList);
        }
    }

    public void addTimeline(Relation relation) {

        for (Post post : relation.getFollowingUser().getPostList()) {

            String followerId = relation.getUser().getUserId();
            // save database
            Timeline timeline = timelineRepository.save(Timeline.builder()
                    .relation(relation)
                    .post(post)
                    .build());

            //update cache
            List<TimelineResponseDto> timelineResponseDtoList = getTimeline(followerId);
            cacheUpdateService.updateAddCache(followerId, timelineResponseDtoList);
        }
    }

    public void deleteTimeline(Post post) {
        User writer = post.getUser();

        for (Relation relation : relationRepository.findByFollowingUser(writer)) {
            Timeline timeline = timelineRepository.findByRelationAndPost(relation, post);
            updateDeleteCache(relation.getUser().getUserId(), timeline);
        }
    }

    public void deleteTimeline(Relation relation) {
        for (Post post : relation.getFollowingUser().getPostList()) {
            Timeline timeline = timelineRepository.findByRelationAndPost(relation, post);
            updateDeleteCache(relation.getUser().getUserId(), timeline);
        }
    }

    @CachePut(value = "timelines", key = "#userId")
    public List<TimelineResponseDto> updateDeleteCache(String userId, Timeline timeline) {
        List<TimelineResponseDto> result = getTimeline(userId);
        result.remove(new TimelineResponseDto(timeline));
        return result;

    }

    private User getUserEntity(String userId, String field) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }
}
