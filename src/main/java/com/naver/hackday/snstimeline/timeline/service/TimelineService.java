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


    /* 캐쉬 사용 함수 */
    /* 캐쉬 사용 시 메소드 실행 하지 않음 - 프린터문 출력되지 않음 */
    @Cacheable(value = "timelines", key = "#userId")
    public List<TimelineResponseDto> getTimeline(String userId) {

        LOG.info("======== Get Timeline Data Not Using Cache!!!!!");
        User user = getUserEntity(userId, "user-id");
        List<Timeline> timelines = timelineRepository.findByUser(user);

        if (timelines.isEmpty()) {
            throw new NoContentException("user-id", "타임라인에 표시할 글이 없습니다.");
        }

        return timelines.stream()
                .map(TimelineResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 팔로잉한 유저가 글을 쓸 경우 */
    /* Timeline DB 에 데이터 추가 */
    /* 글 쓴 유저를 팔로우한 모든 회원의 Cache에 timeline 데이터 추가 */
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

    /* 유저를 팔로우 했을 경우 */
    /* Timeline DB 에 데이터 추가 */
    /* 유저를 팔로우 한 회원의 Cache에 timeline 데이터 추가 */
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

    /* 포스트 내용 변경 시 */
    /* 포스트를 쓴 유저를 팔로우 한 유저들의 Cache에 접근 및 갱신 */
    public void editTimelineWithPost(Post post){

        // Load Timeline From DB with Post Id
        User writer = post.getUser();

        for (Relation relation : relationRepository.findByFollowingUser(writer)) {

            String followerId = relation.getUser().getUserId();

            //update cache
            List<TimelineResponseDto> timelineResponseDtoList = getTimeline(followerId);
            cacheUpdateService.updateAddCache(followerId, timelineResponseDtoList);
        }
    }

    /* 포스트가 삭제되었을 시 */
    /* 포스트한 유저를 팔로우 한 회원들의 Cache에 접근하여 갱신 */
    /* Timeline DB 갱신 */
    public void deleteTimeline(Post post) {

        User writer = post.getUser();
        for (Relation relation : relationRepository.findByFollowingUser(writer)) {
            // Update to Delete Cache
            deleteCache(relation, post);
            // Delete Database
            timelineRepository.deleteByRelationAndPost(relation, post);
        }
    }

    /* A 유저를 언팔로우 했을 경우 */
    /* A가 쓴 글을 언팔로우한 유저의 Cache에서 삭제 */
    /* Timeline DB도 갱신 */
    public void deleteTimeline(Relation relation) {

        for (Post post : relation.getFollowingUser().getPostList()) {
            // Update to Delete Cache
            deleteCache(relation, post);
            // Delete Database
            timelineRepository.deleteByRelationAndPost(relation, post);
        }
    }

    /* A 유저를 언팔로우 했을 경우 */
    /* A가 쓴 글을 언팔로우한 유저의 Cache에서 삭제 */
    private void deleteCache(Relation relation, Post post) {
        String followerId = relation.getUser().getUserId();
        Timeline timeline = timelineRepository.findByRelationAndPost(relation, post);

        List<TimelineResponseDto> timelineResponseDtoList = getTimeline(followerId);
        for(TimelineResponseDto timelineResponseDto : timelineResponseDtoList){
            if(timelineResponseDto.getId() == timeline.getId()) {
                timelineResponseDtoList.remove(timelineResponseDto);
                break;
            }
        }
        cacheUpdateService.updateAddCache(followerId, timelineResponseDtoList);
    }

    private User getUserEntity(String userId, String field) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
    }
}
