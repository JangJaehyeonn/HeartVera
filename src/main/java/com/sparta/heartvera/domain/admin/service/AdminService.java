package com.sparta.heartvera.domain.admin.service;

import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    PostService postService;

    public Object getAllPost(int page) {
        return postService.getAllPostForAdmin(page);
    }
}
