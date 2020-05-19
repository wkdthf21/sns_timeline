package com.naver.hackday.snstimeline.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("delete from PostFile i where i.post.id = :#{#post.id}")
	int deleteFilesByPostId(@Param("post") Post post);

}
