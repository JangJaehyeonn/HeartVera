package com.sparta.heartvera.domain.like.service;

import com.sparta.heartvera.domain.comment.dto.CommentResponseDto;
import com.sparta.heartvera.domain.comment.repository.CommentRepository;
import com.sparta.heartvera.domain.comment.service.CommentService;
import com.sparta.heartvera.domain.like.entity.Like;
import com.sparta.heartvera.domain.like.entity.LikeEnum;
import com.sparta.heartvera.domain.like.repository.LikeRepository;
import com.sparta.heartvera.domain.post.dto.PostResponseDto;
import com.sparta.heartvera.domain.post.entity.Post;
import com.sparta.heartvera.domain.post.service.PostService;
import com.sparta.heartvera.domain.post.service.PublicPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final PublicPostService publicPostService;


    public int countUserLikedPosts(Long userId) {
        return likeRepository.countByUserIdAndContentType(userId, LikeEnum.POST);
    }

    public int countUserLikedComments(Long userId) {
        return likeRepository.countByUserIdAndContentType(userId, LikeEnum.COMMENT);
    }

    // 게시물, 댓글별 좋아요 수 count
    public int getLikesCount(Long contentId, LikeEnum contentType) {
        return likeRepository.countByContentIdAndContentType(contentId, contentType);
    }

    // 익명 게시물별 좋아요 toggle 기능
    @Transactional
    public ResponseEntity<String> togglePostLike(Long userId, Long postId) {
        postService.validatePostLike(userId, postId);
        return toggleLike(userId, postId, LikeEnum.POST);
    }

    // 공개 게시물별 좋아요 toggle 기능
    @Transactional
    public ResponseEntity<String> togglePublicPostLike(Long userId, Long postId) {
        publicPostService.validatePostLike(userId, postId);  // PublicPostService 사용
        return toggleLike(userId, postId, LikeEnum.PUBPOST);
    }

    // 댓글별 좋아요 toggle 기능
    @Transactional
    public ResponseEntity<String> toggleCommentLike(Long userId, Long commentId) {
        commentService.validateCommentLike(userId, commentId);
        return toggleLike(userId, commentId, LikeEnum.COMMENT);
    }

    // 좋아요 토글 기능 (공통 로직)
    private ResponseEntity<String> toggleLike(Long userId, Long contentId, LikeEnum contentType) {
        Optional<Like> likeOptional = findLike(userId, contentId, contentType);
        if (likeOptional.isPresent()) {
            likeRepository.delete(likeOptional.get());
            return ResponseEntity.ok("좋아요를 취소했습니다.");
        } else {
            Like like = new Like(userId, contentId, contentType);
            likeRepository.save(like);
            return ResponseEntity.ok("좋아요를 눌렀습니다.");
        }
    }

    // 좋아요 객체 찾기
    private Optional<Like> findLike(Long userId, Long contentId, LikeEnum contentType) {
        return likeRepository.findByUserIdAndContentIdAndContentType(userId, contentId, contentType);
    }

    // 사용자가 좋아요 한 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getUserLikedPosts(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Long> likedPostIds = likeRepository.findLikedPostIdsByUserId(userId, pageable);
        List<Post> likedPosts = likedPostIds.getContent().stream()
                .map(postService::findById)
                .collect(Collectors.toList());
        return likedPosts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getUserLikedComments(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Like> likes = likeRepository.findByUserIdAndContentType(userId, LikeEnum.COMMENT);
        List<CommentResponseDto> likedComments = likes.stream()
                .map(like -> commentRepository.findById(like.getContentId())
                        .map(CommentResponseDto::new)
                        .orElse(null))
                .filter(comment -> comment != null)
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());
        int start = Math.min((int)pageRequest.getOffset(), likedComments.size());
        int end = Math.min((start + pageRequest.getPageSize()), likedComments.size());
        return new PageImpl<>(likedComments.subList(start, end), pageRequest, likedComments.size());
    }

}