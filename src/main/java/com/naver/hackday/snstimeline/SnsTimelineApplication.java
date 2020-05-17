package com.naver.hackday.snstimeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SnsTimelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsTimelineApplication.class, args);
    }

}
