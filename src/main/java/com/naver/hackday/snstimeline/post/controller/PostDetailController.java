package com.naver.hackday.snstimeline.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.post.dto.PostDto;
import com.naver.hackday.snstimeline.post.service.PostService;

@Api(tags = {"특정 유저의 Post Entity 목록 / 선택한 Post Entity 리턴 API"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostDetailController {

	private final PostService postService;

	// 특정 유저의 Post 목록 반환
	@ApiOperation("특정 유저의 Post Entity 목록 반환")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저의 ID", required = true, dataType = "string", paramType = "path", defaultValue = ""),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post Entity 목록 조회 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 유저"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
	public ResponseEntity<List<PostDto>> searchPostList(@PathVariable(value = "user_id") String userId){
		List<PostDto> postDtoList = postService.searchPostList(userId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);
	}

}
