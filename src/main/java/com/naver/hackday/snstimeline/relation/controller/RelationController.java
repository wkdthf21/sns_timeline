package com.naver.hackday.snstimeline.relation.controller;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.relation.controller.dto.FollowRequestDto;
import com.naver.hackday.snstimeline.relation.service.RelationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RelationController {

    private final RelationService relationService;

    @ApiOperation("친구 구독하기")
    @ApiResponses({
            @ApiResponse(code = 201, message = "친구 구독 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/follow")
    public ResponseEntity<Void> follow(@Valid @RequestBody FollowRequestDto followRequestDto) {

        relationService.follow(followRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //TODO 친구 찾기

    //TODO 친구 구독 취소하기

    //TODO 나를 구독하는 친구 조회하기

    //TODO 내가 구독하는 친구 조회하기
}
