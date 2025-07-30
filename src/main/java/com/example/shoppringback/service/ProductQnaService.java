package com.example.shoppringback.service;

import com.example.shoppringback.dto.ProductQnaDTO;
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
    private final ProductRepository productRepository;

    // 특정 productId에 해당하는 ProductQna 목록 조회 → DTO 리스트로 변환
    public List<ProductQnaDTO> findByProductId(Long productId) {
        List<ProductQna> qnaList = productQnaRepository.findByProductId(productId);
        return qnaList.stream()
                .map(ProductQnaDTO::fromEntity)
                .toList();
    }

    // QnA 하나 조회 (내부용)
    public ProductQna findByIdAndProductId(Long id, Long productId) {
        return productQnaRepository.findByIdAndProductId(id, productId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
    }

    // 게시글 작성 → 저장 후 DTO 반환
    public ProductQnaDTO save(Long productId, ProductQna productQna) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productQna.setProduct(product);
        return ProductQnaDTO.fromEntity(productQnaRepository.save(productQna));
    }

    // 게시글 수정 → 수정 후 DTO 반환
    public ProductQnaDTO update(Long id, Long productId, ProductQna updatedProductQna) {
        ProductQna existing = findByIdAndProductId(id, productId);

        existing.setTitle(updatedProductQna.getTitle());
        existing.setName(updatedProductQna.getName());
        existing.setDate(updatedProductQna.getDate());
        existing.setHit(updatedProductQna.getHit());
        existing.setContent(updatedProductQna.getContent());

        // product 변경 가능
        if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            existing.setProduct(product);
        }

        return ProductQnaDTO.fromEntity(productQnaRepository.save(existing));
    }

    // 게시글 삭제
    public void delete(Long id, Long productId) {
        ProductQna productQna = findByIdAndProductId(id, productId);
        productQnaRepository.delete(productQna);
    }

    // 비밀번호 체크 메서드 (단순 비교 예시)
    public boolean checkPassword(Long id, String password) {
        ProductQna qna = productQnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 예를 들어, 비밀번호가 ProductQna 엔티티에 String password 필드로 저장되어 있다고 가정
        // null 체크와 비교
        return qna.getPassword() != null && qna.getPassword().equals(password);
    }

    public ProductQnaDTO toDto(ProductQna entity) {
        if (entity == null) return null;

        ProductQnaDTO dto = new ProductQnaDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate());
        dto.setHit(entity.getHit());
        dto.setSecret(entity.getSecret());
        dto.setContent(entity.getContent());

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
            dto.setProductName(entity.getProduct().getName());
        }

        return dto;
    }

    // 디테일페이지
    public ProductQnaDTO findById(Long id) {
        ProductQna productQna = productQnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        return toDto(productQna);
    }
}
