package com.naver.hackday.snstimeline.post.domain;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.naver.hackday.snstimeline.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "update Post p set p.contents = :#{#post.contents} WHERE p.id = :#{#post.id}", nativeQuery=false)
	Integer updateContents(@Param("post") Post post);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "delete from Post p where p.id = :#{#post.id}", nativeQuery = false)
	void deletePostById(@Param("post") Post post);

	@Query(value = "select p from Post p where p.user.userId = :#{#user.userId}")
	Optional<List<Post>> findByUserId(@Param("user") User user);
}
