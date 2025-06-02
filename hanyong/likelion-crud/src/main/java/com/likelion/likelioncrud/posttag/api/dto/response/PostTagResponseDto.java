package com.likelion.likelioncrud.posttag.api.dto.response;

import com.likelion.likelioncrud.posttag.domain.PostTag;

public record PostTagResponseDto(//post와 연결된 태그 정보 보여준다
    String tagName//태그 이름
) {
    public static PostTagResponseDto from(PostTag postTag) {
        return new PostTagResponseDto(postTag.getTag().getName());
    }
}
