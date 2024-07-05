package com.sparta.heartvera.domain.like.service;

import com.sparta.heartvera.domain.comment.dto.CommentResponseDto;
import com.sparta.heartvera.domain.comment.entity.Comment;
import com.sparta.heartvera.domain.comment.repository.CommentRepository;
import com.sparta.heartvera.domain.comment.service.CommentService;
import com.sparta.heartvera.domain.like.entity.Like;
import com.sparta.heartvera.domain.like.entity.LikeEnum;
import com.sparta.heartvera.domain.like.repository.LikeRepository;
import com.sparta.heartvera.domain.post.dto.PostResponseDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.service.PostService;
import com.sparta.heartvera.domain.post.service.PublicPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @Mock
    private PublicPostService publicPostService;

    @InjectMocks
    private LikeService likeService;

    private Long userId;
    private Long postId;
    private Long commentId;
    private Like like;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {
        userId = 1L;
        postId = 1L;
        commentId = 1L;
        like = new Like(userId, postId, LikeEnum.POST);
        post = new Post();
        comment = new Comment();
    }

    @Test
    void countUserLikedPosts() {
        when(likeRepository.countByUserIdAndContentType(userId, LikeEnum.POST)).thenReturn(5);

        int count = likeService.countUserLikedPosts(userId);

        assertEquals(5, count);
        verify(likeRepository, times(1)).countByUserIdAndContentType(userId, LikeEnum.POST);
    }

    @Test
    void countUserLikedComments() {
        when(likeRepository.countByUserIdAndContentType(userId, LikeEnum.COMMENT)).thenReturn(3);

        int count = likeService.countUserLikedComments(userId);

        assertEquals(3, count);
        verify(likeRepository, times(1)).countByUserIdAndContentType(userId, LikeEnum.COMMENT);
    }

    @Test
    void togglePostLike() {
        when(likeRepository.findByUserIdAndContentIdAndContentType(userId, postId, LikeEnum.POST))
                .thenReturn(Optional.of(like));

        ResponseEntity<String> response = likeService.togglePostLike(userId, postId);

        assertEquals("좋아요를 취소했습니다.", response.getBody());
        verify(likeRepository, times(1)).delete(like);

        when(likeRepository.findByUserIdAndContentIdAndContentType(userId, postId, LikeEnum.POST))
                .thenReturn(Optional.empty());

        response = likeService.togglePostLike(userId, postId);

        assertEquals("좋아요를 눌렀습니다.", response.getBody());
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void getUserLikedPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> postIds = Arrays.asList(postId, postId + 1);
        Page<Long> likedPostIds = new PageImpl<>(postIds, pageable, postIds.size());

        when(likeRepository.findLikedPostIdsByUserId(userId, pageable)).thenReturn(likedPostIds);
        when(postService.findById(postId)).thenReturn(post);
        when(postService.findById(postId + 1)).thenReturn(new Post());

        List<PostResponseDto> likedPosts = likeService.getUserLikedPosts(userId, 0, 10);

        assertEquals(2, likedPosts.size());
        verify(likeRepository, times(1)).findLikedPostIdsByUserId(userId, pageable);
    }

    @Test
    void getUserLikedComments() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Like> likes = Arrays.asList(new Like(userId, commentId, LikeEnum.COMMENT),
                new Like(userId, commentId + 1, LikeEnum.COMMENT));

        when(likeRepository.findByUserIdAndContentType(userId, LikeEnum.COMMENT)).thenReturn(likes);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.findById(commentId + 1)).thenReturn(Optional.of(new Comment()));

        Page<CommentResponseDto> likedComments = likeService.getUserLikedComments(userId, 0, 10);

        assertEquals(2, likedComments.getContent().size());
        verify(likeRepository, times(1)).findByUserIdAndContentType(userId, LikeEnum.COMMENT);
    }
}
