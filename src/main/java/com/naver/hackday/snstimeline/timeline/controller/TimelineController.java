package com.naver.hackday.snstimeline.timeline.controller;

import com.naver.hackday.snstimeline.common.exception.ExceptionResponseDto;
import com.naver.hackday.snstimeline.timeline.controller.dto.TimelineResponseDto;
import com.naver.hackday.snstimeline.timeline.service.TimelineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@Api(tags = "Timeline API")
@RequiredArgsConstructor
@RestController
public class TimelineController {

    private final TimelineService timelineService;

    private final static Logger LOG = Logger.getGlobal();

    @ApiOperation("타임라인 불러오기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "타임라인 불러오기 성공"),
            @ApiResponse(code = 204, message = "요청 성공했으나 데이터 없음", response = Object.class),
            @ApiResponse(code = 404, message = "존재하지 않는 자원", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("users/{user-id}/timeline")
    public ResponseEntity<List<TimelineResponseDto>> getTimeline(@PathVariable("user-id") String userId) {
        long start = System.currentTimeMillis();
        List<TimelineResponseDto> result = timelineService.getTimeline(userId);
        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        LOG.info("============= 수행 시간 : " + timeTaken);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/cache-delete/{user-id}")
    @CacheEvict(value = "timelines", key = "#userId")
    public void deleteCache(@PathVariable("user-id") String userId) {
    }
}
