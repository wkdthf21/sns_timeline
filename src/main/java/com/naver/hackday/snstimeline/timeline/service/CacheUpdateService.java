package com.naver.hackday.snstimeline.timeline.service;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.naver.hackday.snstimeline.timeline.controller.dto.TimelineResponseDto;

@RequiredArgsConstructor
@Service
public class CacheUpdateService {

	private final static Logger LOG = Logger.getGlobal();

	@CachePut(value = "timelines", key = "#userId")
	public List<TimelineResponseDto> updateAddCache(String userId, List<TimelineResponseDto> timelineResponseDtoList) {
		LOG.info("======== Update Cache!!!!!");
		return timelineResponseDtoList;
	}

}
