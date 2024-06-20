package com.sparta.heartvera.domain.comment.controller;

import com.sparta.heartvera.domain.comment.dto.CommentRequestDto;
import com.sparta.heartvera.domain.comment.dto.CommentResponseDto;
import com.sparta.heartvera.domain.comment.service.CommentService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  // 댓글 작성
  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
      @RequestBody @Valid CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createComment(postId, requestDto, userDetails.getUser()));
  }

  // 선택한 게시물의 댓글 조회
  @GetMapping("/{postId}/comments")
  public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(postId));
  }

  // 댓글 수정
  @PutMapping("/{postId}/comments/{commentId}")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId,
      @PathVariable Long commentId,
      @RequestBody @Valid CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(commentService.updateComment(postId, commentId, requestDto, userDetails.getUser()));
  }

  // 댓글 삭제
  @DeleteMapping("/{postId}/comments/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable Long postId,
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentService.deleteComment(postId, commentId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적으로 삭제되었습니다.");
  }

}
