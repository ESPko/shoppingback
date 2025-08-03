package com.example.shoppringback.repository;

import com.example.shoppringback.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByUserIdAndProductId(Long userId, Long productId);
    boolean existsByUserIdAndProductIdAndLikedTrue(Long userId, Long productId);
    List<UserLike> findByUserIdAndLikedTrue(Long userId);
}



