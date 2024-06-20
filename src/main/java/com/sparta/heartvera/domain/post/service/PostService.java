package com.sparta.heartvera.domain.post.service;

import com.sparta.heartvera.common.exception.CustomException;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.dto.PostResponseDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.repository.PostRepository;
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
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto savePost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(new Post(requestDto, user));

        return new PostResponseDto(post);
    }

    public PostResponseDto getPost(Long postId) {
        Post post = findById(postId);

        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto editPost(Long postId, PostRequestDto requestDto, User user) {
        Post post = findById(postId);
        checkUserSame(post, user);
        post.update(requestDto);

        return new PostResponseDto(post);
    }

    public String deletePost(Long postId, User user) {
        Post post = findById(postId);
        checkUserSame(post, user);
        postRepository.delete(post);

        return postId + "번 게시물 삭제 완료";
    }

    public Object getAllPost(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Post> postList = postRepository.findAll(pageable);

        if (postList.getTotalElements() == 0) {
            throw new CustomException(ErrorCode.POST_EMPTY);
        }

        return postList.map(PostResponseDto::new);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }

    private void checkUserSame(Post post, User user) {
        if (!(post.getUser().getUserSeq().equals(user.getUserSeq()))) {
            throw new CustomException(ErrorCode.POST_NOT_USER);
        }
    }
}
