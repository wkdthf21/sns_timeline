package com.naver.hackday.snstimeline.user.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.common.exception.NotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followings;

    @ManyToMany(mappedBy = "followings")
    private Set<User> followers;

    @Builder
    public User(String userId, String nickname, String profileUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public void addFollowing(User user) {
        if (user.getId().equals(this.id)) {
            throw new NotFoundException("following-id", "스스로를 구독할 수 없습니다.");
        }
        if (!this.followings.add(user)) {
            throw new NotFoundException("following-id", "이미 구독중인 친구입니다.");
        }
    }

    public void cancelFollowing(User user) {
        if (!this.followings.remove(user)) {
            throw new NotFoundException("following-id", "구독중인 친구가 아닙니다.");
        }
    }

    public Boolean isFollowing(User user) {
        for (User u : this.getFollowings()) {
            if (u == user) {
                return true;
            }
        }
        return false;
    }

    public Boolean isFollower(User user) {
        for (User u : this.getFollowers()) {
            if (u == user) {
                return true;
            }
        }
        return false;
    }
}
