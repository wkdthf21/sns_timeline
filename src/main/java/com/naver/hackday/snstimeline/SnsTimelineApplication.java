package com.naver.hackday.snstimeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SnsTimelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsTimelineApplication.class, args);
    }

}
