package com.sparta.heartvera.domain.like.service;

import com.sparta.heartvera.domain.like.entity.Like;
import com.sparta.heartvera.domain.like.entity.LikeEnum;
import com.sparta.heartvera.domain.like.repository.LikeRepository;
import com.sparta.heartvera.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {


    private final LikeRepository likeRepository;
    private final PostService postService;


    // 게시물, 댓글별 좋아요 수 count
    public int getLikesCount(Long contentId, LikeEnum contentType) {
        return likeRepository.countByContentIdAndContentType(contentId, contentType);
    }

    // 게시물별 좋아요 toggle 기능
    public ResponseEntity<String> togglePostLike(Long userId, Long postId) {
        validatePostLike(userId, postId);

        Optional<Like> likeOptional = findLike(userId, postId, LikeEnum.POST);
        if (likeOptional.isPresent()) {
            likeRepository.delete(likeOptional.get());
            return ResponseEntity.ok("좋아요를 취소했습니다.");
        } else {
            Like like = new Like(userId, postId, LikeEnum.POST);
            likeRepository.save(like);
            return ResponseEntity.ok("좋아요를 눌렀습니다.");
        }
    }

    // 댓글별 좋아요 toggle 기능
//    public ResponseEntity<String> toggleCommentLike(Long userId, Long commentId) {
//        validateCommentLike(userId, commentId);
//
//        Optional<Like> likeOptional = findLike(userId, commentId, LikeEnum.COMMENT);
//        if (likeOptional.isPresent()) {
//            likeRepository.delete(likeOptional.get());
//            return ResponseEntity.ok("좋아요를 취소했습니다.");
//        } else {
//            Like like = new Like(userId, commentId, LikeEnum.COMMENT);
//            likeRepository.save(like);
//            return ResponseEntity.ok("좋아요를 눌렀습니다.");
//        }
//    }

    // 유효성 검사
    private void validatePostLike(Long userId, Long postId) {
        postService.validatePostLike(userId, postId);
    }

//    private void validateCommentLike(Long userId, Long commentId) {
//        commentService.validateCommentLike(userId, commentId);
//    }


    // 좋아요 객체 찾기
    private Optional<Like> findLike(Long userId, Long contentId, LikeEnum contentType) {
        return likeRepository.findByUserIdAndContentIdAndContentType(userId, contentId, contentType);
    }

}