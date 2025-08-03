package com.example.shoppringback.service;

import com.example.shoppringback.dto.ProductDTO;
import com.example.shoppringback.entity.Product;
import com.example.shoppringback.entity.User;
import com.example.shoppringback.entity.UserLike;
import com.example.shoppringback.repository.ProductRepository;
import com.example.shoppringback.repository.UserLikeRepository;
import com.example.shoppringback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private UserLikeRepository userLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void toggleLike(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        UserLike like = userLikeRepository.findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (like == null) {
            UserLike newLike = new UserLike();
            newLike.setUser(user);
            newLike.setProduct(product);
            newLike.setLiked(true);
            userLikeRepository.save(newLike);
        } else {
            like.setLiked(!like.isLiked());
            userLikeRepository.save(like);
        }
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Long userId, Long productId) {
        return userLikeRepository.existsByUserIdAndProductIdAndLikedTrue(userId, productId);
    }

    // 찜한 상품 리스트 조회
    public List<ProductDTO> getLikedProductsByUserId(Long userId) {
        List<UserLike> likes = userLikeRepository.findByUserIdAndLikedTrue(userId);

        return likes.stream()
                .map(like -> {
                    var p = like.getProduct();
                    return new ProductDTO(
                            p.getId(),
                            p.getName(),
                            p.getPrice(),
                            p.getSalePrice(),
                            p.getSize(),
                            p.getColor(),
                            p.getCategory(),
                            p.getInfoImage()
                    );
                })
                .collect(Collectors.toList());
    }
}
