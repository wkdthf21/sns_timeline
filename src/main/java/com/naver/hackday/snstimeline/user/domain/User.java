package com.naver.hackday.snstimeline.user.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    private String nickname;

    private String profileUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<Post>();

    @Builder
    public User(String userId, String nickname, String profileUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
