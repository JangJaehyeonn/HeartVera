package com.sparta.heartvera.domain.comment.dto;

import com.sparta.heartvera.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PublicCommentResponseDto {
    private Long id;
    private String userId;
    private Long postId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PublicCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getUserId();
        this.postId = comment.getPost().getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
