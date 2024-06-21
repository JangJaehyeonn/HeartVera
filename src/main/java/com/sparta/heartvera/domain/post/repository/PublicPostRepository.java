package com.sparta.heartvera.domain.post.repository;

import com.sparta.heartvera.domain.post.entity.PublicPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicPostRepository extends JpaRepository<PublicPost, Long> {
}
