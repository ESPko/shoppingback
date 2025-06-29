package com.example.shoppringback.service;

import com.example.shoppringback.entity.Community;
import com.example.shoppringback.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public Page<Community> findAll(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    @Override
    public Optional<Community> findById(Long id) {
        return communityRepository.findById(id);
    }

    @Override
    public Community save(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community update(Long id, Community updatedCommunity) {
        return communityRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedCommunity.getTitle());
                    existing.setContent(updatedCommunity.getContent());
                    existing.setName(updatedCommunity.getName());
                    return communityRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));
    }

    @Override
    public void delete(Long id) {
        communityRepository.deleteById(id);
    }
}

