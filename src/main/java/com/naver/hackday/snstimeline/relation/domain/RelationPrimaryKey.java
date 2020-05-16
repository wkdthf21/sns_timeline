package com.naver.hackday.snstimeline.relation.domain;

import com.naver.hackday.snstimeline.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Builder
@Getter
@Embeddable
public class RelationPrimaryKey {

    @ManyToOne
    private User user;

    @ManyToOne
    private User followingUser;
}
