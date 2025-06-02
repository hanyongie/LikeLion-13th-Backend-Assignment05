package com.likelion.likelioncrud.post.domain;

import com.likelion.likelioncrud.member.domain.Member;
import com.likelion.likelioncrud.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelioncrud.posttag.domain.PostTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.event.spi.PostDeleteEvent;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false) //반드시 존재해야된다
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 설정
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //하나의 Post는 여러 PostTag와 연결, post와 함께 저장, 삭제
    private List<PostTag> postTags = new ArrayList<>(); //여러 postTag를 리스트로 받는다


    @Builder
    private Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(PostUpdateRequestDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.title();
        this.content = postUpdateRequestDto.content();
    }
}
