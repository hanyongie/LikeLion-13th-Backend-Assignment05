package com.likelion.likelioncrud.tag.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagUpdateRequestDto(
        @NotBlank(message = "태그를 작성해주세요")
        @Size(min = 2, message = "2자 이상 입력하세요")
        String tagName //태그명 수정하고 위 에노테이션으로 조건 부여
) {
}
