package com.naver.hackday.snstimeline.user.controller;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.user.controller.dto.UserSearchResponseDto;
import com.naver.hackday.snstimeline.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation("친구 찾기")
    @ApiImplicitParam(name = "userIdOrNickname", value = "찾고 싶은 유저의 로그인 아이디나 닉네임 키워드", example = "test", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "검색 성공"),
            @ApiResponse(code = 404, message = "검색 결과 없음", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/user-search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUser(@RequestParam String userIdOrNickname) {
        List<UserSearchResponseDto> users = userService.searchUser(userIdOrNickname);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
