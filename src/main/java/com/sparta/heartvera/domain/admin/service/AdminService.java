package com.sparta.heartvera.domain.admin.service;

import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostService postService;

    public Object getAllPost(int page) {
        return postService.getAllPostForAdmin(page);
    }


    public Post editPost(Long postId, PostRequestDto requestDto) {
        Post post = postService.findById(postId);
        post.update(requestDto);

        return post;
    }

    public String deletePost(Long postId) {
        Post post = postService.findById(postId);
        postService.delete(post);

        return post.getId() + "번 게시글 삭제에 성공하였습니다.";
    }
}
