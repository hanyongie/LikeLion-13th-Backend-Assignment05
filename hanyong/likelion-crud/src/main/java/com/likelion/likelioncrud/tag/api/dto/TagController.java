package com.likelion.likelioncrud.tag.api.dto;

import com.likelion.likelioncrud.common.error.SuccessCode;
import com.likelion.likelioncrud.common.template.ApiResTemplate;
import com.likelion.likelioncrud.tag.api.dto.request.TagSaveRequestDto;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagInfoResponseDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagListResponseDto;
import com.likelion.likelioncrud.tag.application.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    //태그 저장
    @PostMapping("/save")
    public ApiResTemplate<String> tagSave(@RequestBody @Valid TagSaveRequestDto tagSaveRequestDto) {
        tagService.tagSave(tagSaveRequestDto);
        return ApiResTemplate.successWithContent(SuccessCode.TAG_SAVE_SUCCESS);
    }

    //태그 전체 조회
    @GetMapping("/all")
    public ApiResTemplate<TagListResponseDto> tagFindAll() {
        TagListResponseDto tagListResponseDto = tagService.tagFindAll();
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,tagListResponseDto);
    }
    //태그 id 통해 특정 태그 조회
    @GetMapping("/{tagId}")
    public ApiResTemplate<TagInfoResponseDto> tagFindById(@PathVariable("tagId") Long tagId) {
        TagInfoResponseDto tagInfoResponseDto = tagService.tagFindByOne(tagId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS,tagInfoResponseDto);
    }
    //태그 id 통해 태그 수정(업데이트)
    @PatchMapping("/{tagId}")
    public ApiResTemplate<String> tagUpdate(@PathVariable("tagId") Long tagId, @RequestBody TagUpdateRequestDto tagUpdateRequestDto) {
        tagService.tagUpdate(tagId, tagUpdateRequestDto);
        return ApiResTemplate.successWithContent(SuccessCode.TAG_UPDATE_SUCCESS);
    }
    //태그 id 통해 삭제
    @DeleteMapping("/{tagId}")
    public ApiResTemplate<String> tagDelete(@PathVariable("tagId") Long tagId) {
        tagService.tagDelete(tagId);
        return ApiResTemplate.successWithContent(SuccessCode.TAG_DELETE_SUCCESS);
    }
}

