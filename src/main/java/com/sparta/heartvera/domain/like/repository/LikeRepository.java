package com.sparta.heartvera.domain.like.repository;

import com.sparta.heartvera.domain.like.entity.Like;
import com.sparta.heartvera.domain.like.entity.LikeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 유저 ID , contents ID, contents type으로 해당 좋아요가 있는지 확인
    Optional<Like> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, LikeEnum contentType);
    // 해당 ID를 가진 CONTENTS type이 몇개 있는지 확인
    int countByContentIdAndContentType(Long contentId, LikeEnum contentType);

    @Query("SELECT l.contentId FROM Like l WHERE l.userId = :userId")
    Page<Long> findLikedPostIdsByUserId(Long userId, Pageable pageable);


    List<Like> findByUserIdAndContentType(Long userId, LikeEnum likeEnum);

    int countByUserIdAndContentType(Long userId, LikeEnum likeEnum);
}