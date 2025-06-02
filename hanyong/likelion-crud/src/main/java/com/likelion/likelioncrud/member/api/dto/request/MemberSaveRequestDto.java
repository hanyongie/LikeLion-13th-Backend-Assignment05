package com.likelion.likelioncrud.member.api.dto.request;

import com.likelion.likelioncrud.member.domain.Part;

public record MemberSaveRequestDto( //불변성 보장, dto는 데이터 저장, 전달 목적
        String name,
        int age,
        Part part
) {

}
