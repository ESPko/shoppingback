package com.example.shoppringback.controller;

import com.example.shoppringback.dto.LikeRequest;
import com.example.shoppringback.dto.ProductDTO;
import com.example.shoppringback.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> likeOrUnlikeProduct(@RequestBody LikeRequest request) {
        System.out.println("LikeRequest: userId=" + request.getUserId() + ", productId=" + request.getProductId());

        try {
            likeService.toggleLike(request.getUserId(), request.getProductId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 처리 실패: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> isLiked(@RequestParam Long userId, @RequestParam Long productId) {
        return ResponseEntity.ok(likeService.isLiked(userId, productId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getLikedProducts(@RequestParam Long userId) {
        List<ProductDTO> likedProducts = likeService.getLikedProductsByUserId(userId);
        return ResponseEntity.ok(likedProducts);
    }
}

