package com.naver.hackday.snstimeline.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.post.domain.PostFile;

@Getter
@NoArgsConstructor
public class PostFileDto {

	private Long id;
	private String fileDownloadUri;

	@Builder
	public PostFileDto(PostFile postFile){
		this.id = postFile.getId();
		this.fileDownloadUri = postFile.getUrl();
	}

}
