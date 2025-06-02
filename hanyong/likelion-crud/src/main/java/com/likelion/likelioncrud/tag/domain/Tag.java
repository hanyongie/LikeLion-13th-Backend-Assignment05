package com.likelion.likelioncrud.tag.domain;

import com.likelion.likelioncrud.posttag.domain.PostTag;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tag_id")

    private Long tagId;

    @Column(nullable = false) //반드시 존재
    private String name;

    //PostTag가 Tag와 함께 저장, 삭제
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public void update(TagUpdateRequestDto tagUpdateRequestDto) {
        this.name = tagUpdateRequestDto.tagName(); // 수정된 부분
    }
}
