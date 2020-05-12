package com.naver.hackday.snstimeline.relation.controller;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.relation.controller.dto.FollowResponseDto;
import com.naver.hackday.snstimeline.relation.service.RelationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RelationController {

    private final RelationService relationService;

    @ApiOperation("친구 구독하기")
    @ApiResponses({
            @ApiResponse(code = 201, message = "구독 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/users/{id}/followings/{following-id}")
    public ResponseEntity<Void> follow(@PathVariable Long id,
                                       @PathVariable(value = "following-id") Long followingId) {

        relationService.follow(id, followingId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("내가 구독하는 친구 조회하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회 성공했으나 데이터 없음", response = Object.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/users/{id}/followings")
    public ResponseEntity<List<FollowResponseDto>> getFollowings(@PathVariable Long id) {

        List<FollowResponseDto> followings = relationService.getFollowings(id);

        if (followings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(followings);
    }

    @ApiOperation("나를 구독하는 친구 조회하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회 성공했으나 데이터 없음", response = Object.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/users/{id}/followers")
    public ResponseEntity<List<FollowResponseDto>> getFollowers(@PathVariable Long id) {

        List<FollowResponseDto> followers = relationService.getFollowers(id);

        if (followers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    //TODO 친구 찾기

    //TODO 친구 구독 취소하기

}
