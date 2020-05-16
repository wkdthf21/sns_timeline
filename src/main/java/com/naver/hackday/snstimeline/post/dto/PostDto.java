package com.naver.hackday.snstimeline.post.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@NoArgsConstructor
public class PostDto {

	@NotNull
	private Long id;
	@JsonIgnore
	private User user;
	@Length(max=2000)
	private String contents;

	public void setContents(String contents){
		this.contents = contents;
	}

	public PostDto(Post post){
		this.id = post.getId();
		this.user = post.getUser();
		this.contents = post.getContents();
	}
}
