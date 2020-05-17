package com.naver.hackday.snstimeline.common.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponseDto {

	@ApiModelProperty(notes = "에러 원인", example = "id", required = true, position = 1)
	private String field;

	@ApiModelProperty(notes = "에러 내용", example = "존재하지 않는 유저입니다.", required = true, position = 2)
	private String message;
}