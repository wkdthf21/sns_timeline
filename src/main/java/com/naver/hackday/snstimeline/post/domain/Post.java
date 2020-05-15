package com.naver.hackday.snstimeline.post.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.user.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "posts")
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // JackSon Library 양방향 참조로 인한 무한루프 해결
    // 양방향 참조 필드 중 가져오지 않아도 되는 필드에 어노테이션
    @JsonIgnoreProperties("postList")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(name = "contents", length = 2000)
    private String contents;

    @Builder
    public Post(User user, String contents){

        this.user = user;
        user.getPostList().add(this);

        this.contents = contents;
    }

    public void modifyContents(String contents){
        this.contents = contents;
    }

}
