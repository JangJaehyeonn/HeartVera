package com.sparta.heartvera.domain.like.entity;

import com.sparta.heartvera.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "likes")
public class Like extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "content_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LikeEnum contentType;


    public Like(Long userId, Long contentId, LikeEnum contentType) {
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
    }
}
