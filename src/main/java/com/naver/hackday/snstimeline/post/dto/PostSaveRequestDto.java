package com.naver.hackday.snstimeline.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

	private String userId;
	private String contents;

	public Post toEntity(User user){
		return Post.builder()
				.user(user)
				.contents(contents)
				.build();
	}

}
