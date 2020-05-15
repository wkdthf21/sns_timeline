package com.naver.hackday.snstimeline.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@NoArgsConstructor
public class PostDto {

	private Long id;
	private User user;
	private String contents;

	public void setContents(String contents){
		this.contents = contents;
	}

}
