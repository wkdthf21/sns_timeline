package com.naver.hackday.snstimeline.timeline.domain;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;
import com.naver.hackday.snstimeline.post.domain.Post;
import com.naver.hackday.snstimeline.relation.domain.Relation;
import com.naver.hackday.snstimeline.user.domain.User;
import com.sun.org.glassfish.external.arc.Taxonomy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Relation relation;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Builder
    public Timeline(Relation relation, Post post) {
        this.user = relation.getUser();
        this.relation = relation;
        this.post = post;
    }
}
