package com.naver.hackday.snstimeline.post.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

	@NotEmpty
	private String userId;

	@Length(max=2000)
	private String contents;

	public Post toEntity(User user){
		return Post.builder()
				.user(user)
				.contents(contents)
				.build();
	}

}
