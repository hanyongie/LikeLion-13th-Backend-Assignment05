package com.likelion.likelioncrud.posttag.domain;

import com.likelion.likelioncrud.post.domain.Post;
import com.likelion.likelioncrud.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostTag_id")

    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false) //외래키 이름 설정
    private Post post;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false) //외래키 이름 설정
    private Tag tag;


    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
