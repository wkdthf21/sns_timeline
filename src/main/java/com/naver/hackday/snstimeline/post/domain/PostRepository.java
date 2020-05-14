package com.naver.hackday.snstimeline.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Modifying
	@Query(value = "update Post p set p.contents = :#{#post.contents} WHERE p.id = :#{#post.id}", nativeQuery=false)
	Integer updateContents(@Param("post") Post post);
}
