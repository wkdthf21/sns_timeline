package com.naver.hackday.snstimeline.timeline.controller.dto;

import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.timeline.domain.Timeline;
import com.naver.hackday.snstimeline.user.controller.dto.UserResponseDto;
import com.naver.hackday.snstimeline.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimelineResponseDto {

    @ApiModelProperty(notes = "작성자 정보", position = 1)
    private UserResponseDto writer;

    @ApiModelProperty(notes = "글 아이디", example = "1", position = 2)
    private Long postId;

    @ApiModelProperty(notes = "컨텐츠", example = "안녕하세요", position = 2)
    private String contents;

    @ApiModelProperty(notes = "작성 날짜", example = "테스트1", position = 2)
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
