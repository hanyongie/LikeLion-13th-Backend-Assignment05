package com.likelion.likelioncrud.post.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostSaveRequestDto(
        Long memberId,
        @NotBlank(message = "제목은 필수로 입력해야 합니다.")
        String title,
        @NotBlank(message = "내용은 필수로 입력해야 합니다.")
        String content,
        //post저장 시 같이 저장, 배열로 입력
        List<String> tags
) {
}
