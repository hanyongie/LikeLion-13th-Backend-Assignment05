package com.likelion.likelioncrud.post.api.dto.response;

import com.likelion.likelioncrud.post.domain.Post;
import com.likelion.likelioncrud.posttag.domain.PostTag;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record PostInfoResponseDto(
        String title,
        String content,
        String writer,
        List<String> tags //post에 해당하는 태그 보내준다
) {
    public static PostInfoResponseDto from(Post post) {

        List<String> tags = new ArrayList<>(); //태그 이름 저장할 리스트 생성
        List<PostTag> postTags = post.getPostTags();//post에서 PostTag리스트 가져온다

        if (postTags != null) {//PostTag리스트가 null이 아니면 for문 실행
            for (PostTag postTag : postTags) {
                tags.add(postTag.getTag().getName());//postTag에서 Tag를 가져와 태그이름을 추가
            }
        }


        return PostInfoResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getMember().getName())
                .tags(tags) //tags 추가
                .build();
    }
}