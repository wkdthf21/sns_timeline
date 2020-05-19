package com.naver.hackday.snstimeline.post.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.post.domain.PostFile;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@NoArgsConstructor
/* Post Data를 보여줄때 사용하는 Dto */
public class PostDetailDto {

	@Min(0)
	@NotNull
	private Long id;
	@JsonIgnore
	private User user;
	@Length(max=2000)
	private String contents;
	private List<PostFileDto> fileList = new ArrayList<>();

	public PostDetailDto(Post post, List<PostFile> postFileList){
		this.id = post.getId();
		this.user = post.getUser();
		this.contents = post.getContents();
		for(PostFile postFile : postFileList){
			this.fileList.add(PostFileDto.builder().postFile(postFile).build());
		}
	}
}
