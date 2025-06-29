package com.example.shoppringback.service;


import com.example.shoppringback.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommunityService {
    Page<Community> findAll(Pageable pageable);
    Optional<Community> findById(Long id);
    Community save(Community community);
    Community update(Long id, Community updatedCommunity);
    void delete(Long id);
}

