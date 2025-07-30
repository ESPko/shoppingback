package com.example.shoppringback.controller;

import com.example.shoppringback.dto.ProductQnaDTO;
import com.example.shoppringback.entity.ProductQna;
import com.example.shoppringback.service.ProductQnaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productqna")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductQnaController {

    private final ProductQnaService productQnaService;

    // 특정 productId에 해당하는 ProductQna 목록 조회 (DTO로 반환)
    @GetMapping("/product/{productId}")
    public List<ProductQnaDTO> getProductQnaByProductId(@PathVariable Long productId) {
        return productQnaService.findByProductId(productId);
    }
    // detail페이지 조회용
    @GetMapping("/{id}")
    public ProductQnaDTO getProductQnaById(@PathVariable Long id) {
        return productQnaService.findById(id);
    }

    // ProductQna 게시글 작성 (작성 후 DTO로 반환)
    @PostMapping("/product/{productId}")
    public ProductQnaDTO createProductQna(@PathVariable Long productId, @RequestBody ProductQna productQna) {
        return productQnaService.save(productId, productQna);
    }

    // ProductQna 게시글 수정 (수정 후 DTO로 반환)
    @PutMapping("/{id}/product/{productId}")
    public ProductQnaDTO updateProductQna(@PathVariable Long id, @PathVariable Long productId, @RequestBody ProductQna updatedProductQna) {
        return productQnaService.update(id, productId, updatedProductQna);
    }

    // ProductQna 게시글 삭제
    @DeleteMapping("/{id}/product/{productId}")
    public void deleteProductQna(@PathVariable Long id, @PathVariable Long productId) {
        productQnaService.delete(id, productId);
    }

    @Data
    public static class PasswordCheckRequest {
        private String password;
    }

    @Data
    public static class PasswordCheckResponse {
        private boolean valid;

        public PasswordCheckResponse(boolean valid) {
            this.valid = valid;
        }
    }

    // 비밀번호 체크 API 추가
    @PostMapping("/{id}/check-password")
    public ResponseEntity<PasswordCheckResponse> checkPassword(
            @PathVariable Long id,
            @RequestBody PasswordCheckRequest request
    ) {
        boolean valid = productQnaService.checkPassword(id, request.getPassword());
        return ResponseEntity.ok(new PasswordCheckResponse(valid));
    }
}
