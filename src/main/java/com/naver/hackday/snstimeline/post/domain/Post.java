package com.naver.hackday.snstimeline.post.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.user.domain.User;
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
