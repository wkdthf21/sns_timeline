package com.naver.hackday.snstimeline.user.controller.dto;

import com.naver.hackday.snstimeline.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserResponseDto {

    @ApiModelProperty(notes = "PK 아이디", example = "1", required = true, position = 1)
    private Long id;

    @ApiModelProperty(notes = "로그인 아이디", example = "test1", required = true, position = 2)
    private String userId;

    @ApiModelProperty(notes = "닉네임", example = "테스트1", position = 3)
    private String nickname;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "https://lh3.googleusercontent.com/proxy/CxYnEdUH9eUSq4N8g5pl7EeIePnG3lnD3-4VloIGQA5i3ezYOUnTiQ-59kTxIB3eMO6f54ta6kbuCWgNSDBb", position = 4)
    private String profileUrl;

    @ApiModelProperty(notes = "내가 구독하는 친구인가", example = "true", position = 5)
    private Boolean isFollowing;

    @ApiModelProperty(notes = "나를 구독하는 친구인가", example = "false", position = 6)
    private Boolean isFollower;

    public UserResponseDto(User user, Boolean isFollower, Boolean isFollowing) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.isFollower = isFollower;
        this.isFollowing = isFollowing;
    }
}
