package com.sparta.heartvera.domain.comment.dto;

import com.sparta.heartvera.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long id;
  private String contents;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.contents = comment.getContents();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
  }
}
