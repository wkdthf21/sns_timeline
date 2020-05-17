package com.naver.hackday.snstimeline.relation.domain;

import com.naver.hackday.snstimeline.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findByUser(User user);
    List<Relation> findByFollowingUser(User followingUser);
    Optional<Relation> findByUserAndFollowingUser(User user, User followinUser);
}
