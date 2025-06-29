package com.example.shoppringback.controller;

import com.example.shoppringback.entity.Community;
import com.example.shoppringback.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping
    public Page<Community> getAll(Pageable pageable) {
        return communityService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Community getOne(@PathVariable Long id) {
        return communityService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));
    }

    @PostMapping
    public Community create(@RequestBody Community community) {
        return communityService.save(community);
    }

    @PutMapping("/{id}")
    public Community update(@PathVariable Long id, @RequestBody Community community) {
        return communityService.update(id, community);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        communityService.delete(id);
    }
}
