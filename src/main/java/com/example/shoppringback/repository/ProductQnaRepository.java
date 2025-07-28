package com.example.shoppringback.repository;

import com.example.shoppringback.entity.ProductQna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductQnaRepository extends JpaRepository<ProductQna, Long> {
    List<ProductQna> findByProductId(Long productId); // 특정 productId에 대한 QnA 조회
    Optional<ProductQna> findByIdAndProductId(Long id, Long productId); // 특정 id와 productId로 QnA 조회
    List<ProductQna> findByProductIsNull(); // productId가 null인 QnA 조회
}
