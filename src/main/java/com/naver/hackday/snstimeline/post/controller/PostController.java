package com.naver.hackday.snstimeline.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.post.dto.PostSaveRequestDto;
import com.naver.hackday.snstimeline.post.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	// 포스트 작성
	@ApiOperation("포스트 작성")
	@ApiResponses({
		@ApiResponse(code = 201, message = "포스팅 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 유저"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity uploadPost(@RequestBody PostSaveRequestDto postSaveRequestDto){
		// Upload Post
		postService.uploadPost(postSaveRequestDto);
		// Return
		return new ResponseEntity<>("Success Post Upload", HttpStatus.CREATED);
	}

	// 포스트 삭제
	@ApiOperation("포스트 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "post_id", value = "포스팅 PK", required = true, dataType = "string", paramType = "path", defaultValue = ""),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "포스팅 삭제 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 포스팅"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{post_id}")
	public ResponseEntity deletePost(@PathVariable(value = "post_id") Long id){
		// Delete Post
		postService.deletePost(id);
		// Return
		return new ResponseEntity<>("Success Post Delete", HttpStatus.OK);
	}


}
