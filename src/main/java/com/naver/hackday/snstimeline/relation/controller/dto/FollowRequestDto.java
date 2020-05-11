package com.naver.hackday.snstimeline.relation.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FollowRequestDto {

    @ApiModelProperty(notes = "구독 신청을 하는 유저", example = "1", required = true, position = 1)
    @NotNull
    private Long fromId;

    @ApiModelProperty(notes = "구독 신청을 받는 유저", example = "2", required = true, position = 2)
    @NotNull
    private Long toId;
}
