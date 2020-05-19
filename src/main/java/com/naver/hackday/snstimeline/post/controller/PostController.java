package com.naver.hackday.snstimeline.post.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.post.dto.PostEditRequestDto;
import com.naver.hackday.snstimeline.post.dto.PostSaveRequestDto;
import com.naver.hackday.snstimeline.post.service.PostService;

@Api(tags = {"Post 추가/삭제/수정 API"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	// 포스트 작성
	// 단일 파일 업로드 동작
	@ApiOperation("포스트 작성")
	@ApiResponses({
		@ApiResponse(code = 201, message = "포스팅 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 유저", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity uploadPost(@Valid @ModelAttribute PostSaveRequestDto postSaveRequestDto,
										@RequestPart(value = "file", required = false) MultipartFile file){
		// Upload Post
		if(file != null)
			postService.uploadPost(postSaveRequestDto, file);
		else
			postService.uploadPost(postSaveRequestDto);

		// Return
		return new ResponseEntity<>("Success Post Upload", HttpStatus.CREATED);
	}

	/*
	// 포스트 작성 - 여러 파일 업로드 - Postman에서 동작 / Swagger에서 동작 X
	@ApiOperation("포스트 작성 - 다중 파일")
	@ApiResponses({
		@ApiResponse(code = 201, message = "포스팅 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 유저", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.POST, value = "/multi-file")
	public ResponseEntity uploadPostWithMultiFile(@Valid @ModelAttribute PostSaveRequestDto postSaveRequestDto,
									@RequestPart(value = "files", required = false) List<MultipartFile> files){
		// Upload Post
		postService.uploadPost(postSaveRequestDto);

		// Upload File
		files.stream().map(file -> fileService.saveFile(file));

		// Return
		return new ResponseEntity<>("Success Post Upload", HttpStatus.CREATED);
	}
	*/

	// 포스트 수정
	@ApiOperation("포스트 수정")
	@ApiResponses({
		@ApiResponse(code = 200, message = "포스팅 수정 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 포스팅", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.PUT, value = "/{post-id}")
	public ResponseEntity editPostContents(@Valid @RequestBody PostEditRequestDto postEditRequestDto){
		// Modify Post
		postService.editPostContents(postEditRequestDto);
		// Return
		return new ResponseEntity<>("Success Post Modify", HttpStatus.OK);
	}


	// 포스트 삭제
	@ApiOperation("포스트 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "post-id", value = "포스팅 PK", required = true, dataType = "Long", paramType = "path", example = "1"),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "포스팅 삭제 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 포스팅", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{post-id}")
	public ResponseEntity deletePost(@PathVariable(value = "post-id") @Min(0) Long id){
		// Delete Post
		postService.deletePost(id);
		// Return
		return new ResponseEntity<>("Success Post Delete", HttpStatus.OK);
	}

}
