package com.likelion.likelioncrud.tag.application;

import com.likelion.likelioncrud.common.error.ErrorCode;
import com.likelion.likelioncrud.common.exception.BusinessException;
import com.likelion.likelioncrud.tag.api.dto.request.TagSaveRequestDto;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagInfoResponseDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagListResponseDto;
import com.likelion.likelioncrud.tag.domain.Tag;
import com.likelion.likelioncrud.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRespository;

    //태그명 저장
    @Transactional
    public void tagSave(TagSaveRequestDto tagSaveRequestDto) {
        Tag tag = Tag.builder()
                .name(tagSaveRequestDto.tagName())
                .build();
        tagRespository.save(tag);
    }
    //모든 태그를 조회
    public TagListResponseDto tagFindAll() {
        List<Tag> tags = tagRespository.findAll();
        List<TagInfoResponseDto> tagInfoResponseDtoList = tags.stream()
                .map(TagInfoResponseDto::from)
                .toList();
        return TagListResponseDto.from(tagInfoResponseDtoList);
    }
    //단일 태그를 조회
    public TagInfoResponseDto tagFindByOne(Long tagid) {
        Tag tag = tagRespository
                .findById(tagid)
                .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION,ErrorCode.TAG_NOT_FOUND_EXCEPTION.getMessage()+tagid));
        return TagInfoResponseDto.from(tag);
    }
    //태그 수정
    @Transactional
    public void tagUpdate(Long tagId, TagUpdateRequestDto tagUpdateRequestDto) {
        Tag tag = tagRespository.findById(tagId).orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION,ErrorCode.TAG_NOT_FOUND_EXCEPTION.getMessage() + tagId));
        tag.update(tagUpdateRequestDto);
    }
    //태그 삭제
    @Transactional
    public void tagDelete(Long tagId) {
        Tag tag = tagRespository.findById(tagId).orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION,ErrorCode.TAG_NOT_FOUND_EXCEPTION.getMessage()));
        tagRespository.delete(tag);
    }
}

