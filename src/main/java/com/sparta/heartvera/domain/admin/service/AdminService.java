package com.sparta.heartvera.domain.admin.service;

import com.sparta.heartvera.domain.comment.dto.PublicCommentResponseDto;
import com.sparta.heartvera.domain.comment.entity.Comment;
import com.sparta.heartvera.domain.comment.service.CommentService;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.entity.PublicPost;
import com.sparta.heartvera.domain.post.service.PostService;
import com.sparta.heartvera.domain.post.service.PublicPostService;
import com.sparta.heartvera.domain.user.entity.User;
import com.sparta.heartvera.domain.user.entity.UserRoleEnum;
import com.sparta.heartvera.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostService postService;
    private final PublicPostService publicPostService;
    private final UserService userService;
    private final CommentService commentService;

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

    public Object getAllPublicPost(int page) {
        return publicPostService.getAllPostForAdmin(page);
    }


    public PublicPost editPublicPost(Long postId, PostRequestDto requestDto) {
        PublicPost post = publicPostService.findById(postId);
        post.update(requestDto);

        return post;
    }

    public String deletePublicPost(Long postId) {
        PublicPost post = publicPostService.findById(postId);
        publicPostService.delete(post);

        return post.getId() + "번 게시글 삭제에 성공하였습니다.";
    }

    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    @Transactional
    public String changeUserAuthority(Long userId) {
        User user = userService.findByUserSeq(userId);

        if(user.getAuthority() == UserRoleEnum.USER) {
            user.setUserRole(UserRoleEnum.ADMIN);
        } else {
            user.setUserRole(UserRoleEnum.USER);
        }

        return user.getUserName() + " 회원의 권한이 " + user.getAuthority() + "으로 변경되었습니다.";
    }

    public List<PublicCommentResponseDto> getAllComment() {
        return commentService.getAllCommentForAdmin();
    }
}
