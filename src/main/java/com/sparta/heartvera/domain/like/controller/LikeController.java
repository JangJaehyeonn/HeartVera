package com.sparta.heartvera.domain.like.controller;

import com.sparta.heartvera.domain.like.dto.LikeCountResponseDto;
import com.sparta.heartvera.domain.like.entity.LikeEnum;
import com.sparta.heartvera.domain.like.service.LikeService;
import com.sparta.heartvera.domain.post.dto.PostResponseDto;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "좋아요 API",description = "좋아요 관련된 기능을 담당하는 API 입니다.")
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    //익명 게시물별 좋아요 수 조회
    @Operation(summary = "익명 게시물별 좋아요 수 조회",description = "익명 게시물별 좋아요 수를 조회합니다.")
    @GetMapping("/posts/{postId}/like/count")
    public LikeCountResponseDto getPostLikes(@PathVariable("postId") Long postId) {
        return new LikeCountResponseDto(likeService.getLikesCount(postId, LikeEnum.POST));
    }

    // 댓글별 좋아요 수 조회
    @Operation(summary = "댓글별 좋아요 수 조회",description = "댓글별 좋아요 수를 조회합니다.")
    @GetMapping("/posts/{postId}/comments/{commentsId}/like/count")
    public LikeCountResponseDto getCommentLikes(@PathVariable("commentsId") Long commentsId) {
        return new LikeCountResponseDto(likeService.getLikesCount(commentsId, LikeEnum.COMMENT));
    }

    // 공개 게시물별 좋아요 수 조회
    @Operation(summary = "일반 게시물별 좋아요 수 조회",description = "일반 게시물별 좋아요 수를 조회합니다.")
    @GetMapping("/pubposts/{postId}/like/count")
    public LikeCountResponseDto getPublicPostLikes(@PathVariable("postId") Long postId) {
        return new LikeCountResponseDto(likeService.getLikesCount(postId, LikeEnum.PUBPOST));
    }

    // 익명 게시물별 좋아요 토글
    @Operation(summary = "익명 게시물별 좋아요 토글",description = "익명 게시물별 좋아요를 추가/삭제합니다.")
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> togglePostLike(@PathVariable(name = "postId") long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.togglePostLike(userDetails.getUser().getUserSeq(), postId);
    }

    // 댓글별 좋아요 토글
    @Operation(summary = "댓글별 좋아요 토글",description = "댓글별 좋아요를 추가/삭제합니다.")
    @PostMapping("/posts/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> toggleCommentLike(@PathVariable(name = "commentId") long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.toggleCommentLike(userDetails.getUser().getUserSeq(), commentId);
    }

    // 공개 게시물별 좋아요 토글
    @Operation(summary = "공개 게시물별 좋아요 토글",description = "공개 게시물별 좋아요를 추가/삭제합니다.")
    @PostMapping("/pubposts/{postId}/like")
    public ResponseEntity<String> togglePublicPostLike(@PathVariable(name = "postId") long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.togglePublicPostLike(userDetails.getUser().getUserSeq(), postId);
    }

    // 사용자가 좋아요 한 게시글 목록 조회
    @Operation(summary = "사용자가 좋아요 한 게시글 목록 조회", description = "사용자가 좋아요 한 게시글 목록을 조회합니다.")
    @GetMapping("/liked-posts")
    public List<PostResponseDto> getUserLikedPosts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        return likeService.getUserLikedPosts(userDetails.getUser().getUserSeq(), page, size);
    }

}