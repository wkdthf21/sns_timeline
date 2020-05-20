package com.naver.hackday.snstimeline.relation.controller;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.relation.controller.dto.RelationUserResponseDto;
import com.naver.hackday.snstimeline.relation.service.RelationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Relation API")
@RequiredArgsConstructor
@RestController
public class RelationController {

    private final RelationService relationService;

    @ApiOperation("친구 구독하기")
    @ApiResponses({
            @ApiResponse(code = 201, message = "구독 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ExceptionResponseDto.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/users/{user-id}/followings/{following-id}")
    public ResponseEntity<Void> follow(@PathVariable(value = "user-id") String userId,
                                       @PathVariable(value = "following-id") String followingId) {

        relationService.follow(userId, followingId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("내가 구독하는 친구 조회하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회 성공했으나 데이터 없음", response = Object.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/users/{user-id}/followings")
    public ResponseEntity<List<RelationUserResponseDto>> getFollowings(@PathVariable(value = "user-id") String userId) {

        List<RelationUserResponseDto> followings = relationService.getFollowings(userId);

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
    @GetMapping("/users/{user-id}/followers")
    public ResponseEntity<List<RelationUserResponseDto>> getFollowers(@PathVariable(value = "user-id") String userId) {

        List<RelationUserResponseDto> followers = relationService.getFollowers(userId);

        if (followers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @ApiOperation("친구 구독 취소하기")
    @ApiResponses({
            @ApiResponse(code = 204, message = "구독 취소 성공", response = Object.class),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ExceptionResponseDto.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @DeleteMapping("/user/{user-id}/followings/{following-id}")
    public ResponseEntity<Void> cancelFollowing(@PathVariable(value = "user-id") String userId,
                                                @PathVariable(value = "following-id") String followingId) {

        relationService.cancelFollow(userId, followingId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
