package com.likelion.likelioncrud.member.api.dto.response;

import com.likelion.likelioncrud.member.domain.Member;
import com.likelion.likelioncrud.member.domain.Part;
import lombok.Builder;

//사용자로부터 값을 보여주는 역할 response
@Builder
public record MemberInfoResponseDto(
        String name,
        int age,
        Part part
) {
    public static MemberInfoResponseDto from(Member member) {
        return MemberInfoResponseDto.builder()
                .name(member.getName())
                .age(member.getAge())
                .part(member.getPart())
                .build();
    }
}
