package com.naver.hackday.snstimeline.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.post.domain.PostFile;
import com.naver.hackday.snstimeline.post.domain.PostFileRepository;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.post.domain.PostRepository;
import com.naver.hackday.snstimeline.post.dto.PostDetailDto;
import com.naver.hackday.snstimeline.post.dto.PostEditRequestDto;
import com.naver.hackday.snstimeline.post.dto.PostSaveRequestDto;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class PostService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostFileRepository postFileRepository;

	private final FileService fileService;

	@Transactional
	public void uploadPost(PostSaveRequestDto postSaveRequestDto){
		// Find User
		// Exception : Not Found User
		User user = this.getUserEntity(postSaveRequestDto.getUserId(), "user_id");
		// Save Post Data
		Post post = postRepository.save(postSaveRequestDto.toEntity(user));
	}

	@Transactional
	public void uploadPost(PostSaveRequestDto postSaveRequestDto, MultipartFile file){
		// Find User
		// Exception : Not Found User
		User user = this.getUserEntity(postSaveRequestDto.getUserId(), "user_id");
		// Save Post Data
		Post post = postRepository.save(postSaveRequestDto.toEntity(user));
		// Upload Image File
		String fileName = fileService.saveFile(file);
		// Save Image Data
		postFileRepository.save(PostFile.builder()
				.url(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadFile/")
					.path(fileName)
					.toUriString())
				.post(post)
				.build());
	}


	@Transactional
	public void editPostContents(PostEditRequestDto postEditRequestDto){
		// Find Post
		// Exception : Not Found Post
		Post post = this.getPostEntity(postEditRequestDto.getId());
		// Modify Post
		post.modifyContents(postEditRequestDto.getContents());
		postRepository.updateContents(post);
	}

	@Transactional
	public void deletePost(Long id){
		// Find Post
		// Exception : Not Found Post
		Post post = this.getPostEntity(id);

		// Delete Post
		postFileRepository.deleteFilesByPostId(post);
		postRepository.deletePostById(post);
	}
	
	@Transactional
	public List<PostDetailDto> searchPostList(String userId){
		// Find User
		// Exception : Not Found User
		User user = this.getUserEntity(userId, "user_id");
		// Return PostDto List
		return postRepository.findByUserId(user)
			.orElseGet(ArrayList<Post>::new)
			.stream()
			.map(post -> new PostDetailDto(post, post.getPostFileList()))
			.collect(Collectors.toList());
	}

	@Transactional
	public PostDetailDto getPostDetail(Long postId){
		// Find Post
		// Exception : Not Found Post
		Post post = this.getPostEntity(postId);
		// Return PostDto
		return new PostDetailDto(post, post.getPostFileList());
	}

	private Post getPostEntity(Long id){
		return postRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("post_id", "존재하지 않는 포스팅입니다."));
	}

	private User getUserEntity(String userId, String field) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
	}

}
