package com.sparta.heartvera.domain.post.service;

import com.sparta.heartvera.common.exception.CustomException;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.dto.PostResponseDto;
import com.sparta.heartvera.domain.post.dto.PublicPostResponseDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.entity.PublicPost;
import com.sparta.heartvera.domain.post.repository.PublicPostRepository;
import com.sparta.heartvera.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicPostService {

    private final PublicPostRepository postRepository;

    public PublicPostResponseDto savePost(PostRequestDto requestDto, User user) {
        PublicPost post = postRepository.save(new PublicPost(requestDto, user));

        return new PublicPostResponseDto(post);
    }

    public PublicPostResponseDto getPost(Long postId) {
        PublicPost post = findById(postId);

        return new PublicPostResponseDto(post);
    }

    @Transactional
    public PublicPostResponseDto editPost(Long postId, PostRequestDto requestDto, User user) {
        PublicPost post = findById(postId);
        checkUserSame(post, user);
        post.update(requestDto);

        return new PublicPostResponseDto(post);
    }

    public String deletePost(Long postId, User user) {
        PublicPost post = findById(postId);
        checkUserSame(post, user);
        postRepository.delete(post);

        return postId + "번 게시물 삭제 완료";
    }

    public Object getAllPost(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<PublicPost> postList = postRepository.findAll(pageable);

        if (postList.getTotalElements() == 0) {
            throw new CustomException(ErrorCode.POST_EMPTY);
        }

        return postList.map(PublicPostResponseDto::new);
    }

    public PublicPost findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }

    private void checkUserSame(PublicPost post, User user) {
        if (!(post.getUser().getUserSeq().equals(user.getUserSeq()))) {
            throw new CustomException(ErrorCode.POST_NOT_USER);
        }
    }

    public Object getAllPostForAdmin(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<PublicPost> postList = postRepository.findAll(pageable);

        if (postList.getTotalElements() == 0) {
            throw new CustomException(ErrorCode.POST_EMPTY);
        }

        return postList.map(PublicPostResponseDto::new);
    }

    public void delete(PublicPost post) {
        postRepository.delete(post);
    }
}
