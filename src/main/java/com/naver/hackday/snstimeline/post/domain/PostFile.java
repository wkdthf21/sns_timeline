package com.naver.hackday.snstimeline.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;


@Getter
@NoArgsConstructor
@Table(name = "post_files")
@Entity
public class PostFile extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;

	@ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private Post post;

	@Column(name = "url", length = 200)
	private String url;

	@Builder
	public PostFile(Post post, String url){

		this.post = post;
		post.getPostFileList().add(this);

		this.url = url;
	}

}
