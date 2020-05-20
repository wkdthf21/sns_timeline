package com.naver.hackday.snstimeline.post.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;


@Getter
/* Post Data 수정 요청 시 사용하는 Dto */
/* 내용 수정만 */
public class PostEditRequestDto {

	@Min(0)
	@NotNull
	private Long id;

	@Length(max=2000)
	private String contents;

}
