package com.example.shoppringback.repository;

import com.example.shoppringback.entity.QnA;
import com.example.shoppringback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnA, Long> {

    // 특정 product에 속하는 QnA 목록 조회
    List<QnA> findByProduct(Product product);

    // productId가 null인 QnA 목록 조회
    List<QnA> findByProductIsNull();  // product 필드가 null인 QnA만 조회
}
