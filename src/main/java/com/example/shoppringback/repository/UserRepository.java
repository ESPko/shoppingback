package com.example.shoppringback.repository;

import com.example.shoppringback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
