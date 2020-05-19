package com.naver.hackday.snstimeline.post.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.post.domain.Image;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;

@Getter
@NoArgsConstructor
public class PostDto {

	@Min(0)
	@NotNull
	private Long id;
	@JsonIgnore
	private User user;
	@Length(max=2000)
	private String contents;
	private List<ImageDto> fileList = new ArrayList<>();

	public PostDto(Post post, List<Image> imageList){
		this.id = post.getId();
		this.user = post.getUser();
		this.contents = post.getContents();
		for(Image image : imageList){
			fileList.add(ImageDto.builder()
							.fileDownloadUri(image.getImagesUrl())
							.build());
		}
	}
}
