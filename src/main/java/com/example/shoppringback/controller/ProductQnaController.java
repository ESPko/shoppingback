package com.example.shoppringback.controller;

import com.example.shoppringback.entity.ProductQna;
import com.example.shoppringback.service.ProductQnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productqna")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductQnaController {

    private final ProductQnaService productQnaService;

    // 특정 productId에 해당하는 ProductQna 목록 조회
    @GetMapping("/product/{productId}")
    public List<ProductQna> getProductQnaByProductId(@PathVariable Long productId) {
        return productQnaService.findByProductId(productId);
    }

    // productId가 null인 ProductQna 목록 조회
    @GetMapping("/null")
    public List<ProductQna> getProductQnaWithNullProduct() {
        return productQnaService.findByProductIsNull();
    }

    // ProductQna 게시글 작성
    @PostMapping("/product/{productId}")
    public ProductQna createProductQna(@PathVariable Long productId, @RequestBody ProductQna productQna) {
        return productQnaService.save(productId, productQna);
    }

    // ProductQna 게시글 수정
    @PutMapping("/{id}/product/{productId}")
    public ProductQna updateProductQna(@PathVariable Long id, @PathVariable Long productId, @RequestBody ProductQna updatedProductQna) {
        return productQnaService.update(id, productId, updatedProductQna);
    }

    // ProductQna 게시글 삭제
    @DeleteMapping("/{id}/product/{productId}")
    public void deleteProductQna(@PathVariable Long id, @PathVariable Long productId) {
        productQnaService.delete(id, productId);
    }
}
