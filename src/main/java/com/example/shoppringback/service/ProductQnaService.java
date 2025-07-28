package com.example.shoppringback.service;

import com.example.shoppringback.entity.Product;
import com.example.shoppringback.entity.ProductQna;
import com.example.shoppringback.repository.ProductQnaRepository;
import com.example.shoppringback.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQnaService {

    private final ProductQnaRepository productQnaRepository;
    private final ProductRepository productRepository; // ProductRepository 추가

    // 특정 productId에 해당하는 ProductQna 목록 조회
    public List<ProductQna> findByProductId(Long productId) {
        return productQnaRepository.findByProductId(productId);
    }

    // productId가 null인 ProductQna 목록 조회
    public List<ProductQna> findByProductIsNull() {
        return productQnaRepository.findByProductIsNull();
    }

    // ProductQna 게시글 하나 조회
    public ProductQna findByIdAndProductId(Long id, Long productId) {
        return productQnaRepository.findByIdAndProductId(id, productId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
    }

    // ProductQna 게시글 작성
    public ProductQna save(Long productId, ProductQna productQna) {
        // productId를 사용해 Product 객체를 찾아 설정
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productQna.setProduct(product); // setProduct를 사용하여 Product 객체 설정
        return productQnaRepository.save(productQna);
    }

    // ProductQna 게시글 수정
    public ProductQna update(Long id, Long productId, ProductQna updatedProductQna) {
        ProductQna existing = findByIdAndProductId(id, productId);
        existing.setTitle(updatedProductQna.getTitle());
        existing.setName(updatedProductQna.getName());
        existing.setDate(updatedProductQna.getDate());
        existing.setHit(updatedProductQna.getHit());
        existing.setContent(updatedProductQna.getContent());

        // productId로 새로운 Product 객체를 찾아 설정
        if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            existing.setProduct(product); // 새로운 Product 객체로 수정
        }

        return productQnaRepository.save(existing);
    }

    // ProductQna 게시글 삭제
    public void delete(Long id, Long productId) {
        ProductQna productQna = findByIdAndProductId(id, productId);
        productQnaRepository.delete(productQna);
    }
}
