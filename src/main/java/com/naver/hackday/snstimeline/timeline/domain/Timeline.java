package com.naver.hackday.snstimeline.timeline.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.user.domain.User;
import lombok.Builder;

import javax.persistence.*;

@Table(name = "timelines")
@Entity
public class Timeline extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeline_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @ManyToOne
    private Post post;

    @Builder
    public Timeline(User user, User writer, Post post) {
        this.user = user;
        this.writer = writer;
        this.post = post;
    }
}
