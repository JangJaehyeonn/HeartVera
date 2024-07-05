package com.sparta.heartvera.domain.user.repository;

import com.sparta.heartvera.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserName(String userName);
    // 사용자가 좋아요한 게시글 수 조회
    @Query("SELECT COUNT(p) FROM User u JOIN u.likedPosts p WHERE u.userSeq = :userId")
    int countLikedPostsById(Long userId);

    // 사용자가 좋아요한 댓글 수 조회
    @Query("SELECT COUNT(c) FROM User u JOIN u.likedComments c WHERE u.userSeq = :userId")
    int countLikedCommentsById(Long userId);
    Optional<User> findByRefreshToken(String refreshtoken);

}
