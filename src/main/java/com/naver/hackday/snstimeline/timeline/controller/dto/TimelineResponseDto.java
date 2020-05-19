package com.naver.hackday.snstimeline.timeline.controller.dto;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.timeline.domain.Timeline;
import com.naver.hackday.snstimeline.user.controller.dto.UserResponseDto;
import com.naver.hackday.snstimeline.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimelineResponseDto {

    private UserResponseDto writer;

    private Long postId;

    private String contents;

    private LocalDateTime createdDate;

    public TimelineResponseDto(Timeline timeline) {
        User writer = timeline.getRelation().getFollowingUser();
        this.writer = new UserResponseDto(writer);

        Post post = timeline.getPost();
        this.postId = post.getId();
        this.contents = post.getContents();
        this.createdDate = post.getCreatedDate();
    }
}
