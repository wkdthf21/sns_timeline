package com.naver.hackday.snstimeline.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.post.domain.PostRepository;
import com.naver.hackday.snstimeline.post.dto.PostDto;
import com.naver.hackday.snstimeline.post.dto.PostSaveRequestDto;
import com.naver.hackday.snstimeline.user.domain.User;
import com.naver.hackday.snstimeline.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class PostService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void uploadPost(PostSaveRequestDto postSaveRequestDto){
		// Find User
		// Exception : Not Found User
		User user = this.getUserEntity(postSaveRequestDto.getUserId(), "user_id");
		// Create Post
		postRepository.save(postSaveRequestDto.toEntity(user));
	}

	@Transactional
	public void editPostContents(PostDto postDto){
		// Find Post
		// Exception : Not Found Post
		Post post = this.getPostEntity(postDto.getId());
		// Modify Post
		post.modifyContents(postDto.getContents());
		postRepository.updateContents(post);
	}

	@Transactional
	public void deletePost(Long id){
		// Find Post
		// Exception : Not Found Post
		Post post = this.getPostEntity(id);
		// Delete Post
		postRepository.deletePostById(post);
	}
	
	@Transactional
	public List<PostDto> searchPostList(String userId){
		// Find User
		// Exception : Not Found User
		User user = this.getUserEntity(userId, "user_id");
		// Return PostDto List
		return postRepository.findByUserId(user)
			.orElseGet(ArrayList<Post>::new)
			.stream()
			.map(PostDto::new)
			.collect(Collectors.toList());
	}

	private Post getPostEntity(Long id){
		return postRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("post_id", "존재하지 않는 포스팅입니다."));
	}

	private User getUserEntity(String userId, String field) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new NotFoundException(field, "존재하지 않는 유저입니다."));
	}

}
