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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.naver.hackday.snstimeline.common.BaseTimeEntity;


@Getter
@NoArgsConstructor
@Table(name = "images")
@Entity
public class Image extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long id;

	@ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private Post post;

	@Column(name = "images_url", length = 200)
	private String imagesUrl;

	@Builder
	public Image(Post post, String imagesUrl){

		this.post = post;
		post.getImageList().add(this);

		this.imagesUrl = imagesUrl;
	}

}
