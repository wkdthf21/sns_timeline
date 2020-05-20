package com.naver.hackday.snstimeline.relation.domain;

import com.naver.hackday.snstimeline.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findByUser(User user);
    List<Relation> findByFollowingUser(User followingUser);
    Optional<Relation> findByUserAndFollowingUser(User user, User followingUser);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Relation R where R.id = :#{#relation.id}", nativeQuery = false)
    void delete(@Param("relation") Relation relation);
}
