package com.naver.hackday.snstimeline.timeline.domain;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findByUser(User user);

    Timeline findByRelationAndPost(Relation relation, Post post);

    int deleteByPost(Post post);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Timeline T where T.post.id in :#{#postIds} and T.relation.id = :#{#relation.id}", nativeQuery = false)
    int deleteAllByPostIdAndRelationId(@Param("postIds") List<Long> postIds, @Param("relation") Relation relation);


}
