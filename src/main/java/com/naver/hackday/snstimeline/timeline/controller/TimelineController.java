package com.naver.hackday.snstimeline.timeline.controller;

import com.naver.hackday.snstimeline.timeline.controller.dto.TimelineResponseDto;
import com.naver.hackday.snstimeline.timeline.service.TimelineService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Timeline API")
@RequiredArgsConstructor
@RestController
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping("users/{user-id}/timeline")
    public List<TimelineResponseDto> getTimeline(@PathVariable("user-id") String userId) {
        return timelineService.getTimeline(userId);
    }
}
