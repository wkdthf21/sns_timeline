package com.naver.hackday.snstimeline.post.controller;

import java.util.List;

import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.common.exception.FileUploadDownloadException;
import com.naver.hackday.snstimeline.post.dto.PostDetailDto;
import com.naver.hackday.snstimeline.post.service.FileService;
import com.naver.hackday.snstimeline.post.service.PostService;

@Api(tags = {"Post Detail API"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostDetailController {

	private final PostService postService;
	private final FileService fileService;

	// 특정 유저의 Post 목록 반환
	@ApiOperation("특정 유저의 Post Entity 목록 반환")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user-id", value = "유저의 ID", required = true, dataType = "string", paramType = "path", defaultValue = ""),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post Entity 목록 조회 성공"),
		@ApiResponse(code = 204, message = "Post Entity 목록 조회 성공했으나, 데이터 없음", response = Object.class),
		@ApiResponse(code = 404, message = "존재하지 않는 유저", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/users/{user-id}")
	public ResponseEntity<List<PostDetailDto>> searchPostList(@PathVariable(value = "user-id") @NotEmpty String userId){
		List<PostDetailDto> postDetailDtoList = postService.searchPostList(userId);
		if(postDetailDtoList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(postDetailDtoList, HttpStatus.OK);
	}

	// 선택한 Post Entity 반환
	// 유저 개인의 피드에 있는 글 선택 OR 수정 선택 시 기존 정보 불러올 때
	@ApiOperation("선택한 Post Entity 반환")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "post-id", value = "포스팅 PK", required = true, dataType = "Long", paramType = "path", example = "1"),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post Entity 조회 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 포스트", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/{post-id}")
	public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable(value = "post-id") @Min(0) Long postId){
		PostDetailDto postDetailDto = postService.getPostDetail(postId);
		return new ResponseEntity<>(postDetailDto, HttpStatus.OK);
	}


	// 이미지 다운로드
	@ApiOperation("이미지 다운로드")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file-name", value = "파일 이름", required = true, dataType = "String", paramType = "path", example = "1"),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "다운로드 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/downloadFile/{file-name}")
	public ResponseEntity<Resource> downloadFile(@PathVariable(value = "file-name") String fileName,
										HttpServletRequest request){
		Resource resource = fileService.downloadFileAsResource(fileName);
		String contentType;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch (Exception e){
			throw new FileUploadDownloadException("file-type", "파일 타입을 지정할 수 없습니다.");
		}
		contentType = contentType == null ? "application/octet-stream" : contentType;

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
