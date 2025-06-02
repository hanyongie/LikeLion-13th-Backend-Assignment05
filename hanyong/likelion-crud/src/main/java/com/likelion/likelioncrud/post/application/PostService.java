package com.likelion.likelioncrud.post.application;

import com.likelion.likelioncrud.common.error.ErrorCode;
import com.likelion.likelioncrud.common.exception.BusinessException;
import com.likelion.likelioncrud.member.domain.Member;
import com.likelion.likelioncrud.member.domain.repository.MemberRepository;
import com.likelion.likelioncrud.post.api.dto.request.PostSaveRequestDto;
import com.likelion.likelioncrud.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelioncrud.post.api.dto.response.PostInfoResponseDto;
import com.likelion.likelioncrud.post.api.dto.response.PostListResponseDto;
import com.likelion.likelioncrud.post.domain.Post;
import com.likelion.likelioncrud.post.domain.repository.PostRepository;
import com.likelion.likelioncrud.posttag.domain.PostTag;
import com.likelion.likelioncrud.tag.domain.Tag;
import com.likelion.likelioncrud.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    // 게시물 저장
    @Transactional
    public void postSave(PostSaveRequestDto postSaveRequestDto) {

        Member member = memberRepository.findById(postSaveRequestDto.memberId()).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION
                        , ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        Post post = Post.builder()
                .title(postSaveRequestDto.title())
                .content(postSaveRequestDto.content())
                .member(member)
                .build();
        //tag
        List<String> tags = postSaveRequestDto.tags();
        if(tags != null && !tags.isEmpty()) {//기존에 태그가 있는지 확인
            for (String tagName : tags) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = Tag.builder().name(tagName).build();
                            return tagRepository.save(newTag);
                        });

                //PostTag 생성 및 연결
                PostTag postTag = new PostTag(post, tag);
                post.getPostTags().add(postTag);
            }
            postRepository.save(post);
        }


    }

    // 게시글 id로 게시글 조회
    public PostInfoResponseDto postFindById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION, ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId));
        return PostInfoResponseDto.from(post);
    }

    // 특정 작성자가 작성한 게시글 목록을 조회
    public PostListResponseDto postFindByMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION
                        , ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage() + memberId));

        List<Post> posts = postRepository.findByMember(member);
        List<PostInfoResponseDto> postInfoResponseDto = posts.stream()
                .map(PostInfoResponseDto::from)
                .toList();

        return PostListResponseDto.from(postInfoResponseDto);
    }

    // 게시물 수정
    @Transactional
    public void postUpdate(Long postId, PostUpdateRequestDto postUpdateRequestDto)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION
                        , ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId)
        );
        post.update(postUpdateRequestDto);
    }

    // 게시물 삭제
    @Transactional
    public void postDelete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION
                        , ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId)
        );
        postRepository.delete(post);
    }
}