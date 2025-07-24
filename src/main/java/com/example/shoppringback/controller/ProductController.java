package com.example.shoppringback.controller;


import com.example.shoppringback.entity.Product;
import com.example.shoppringback.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품 리스트
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByFilter(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category
    ) {
        List<Product> products;
        if (type != null && category != null) {
            products = productService.getProductsByTypeAndCategory(type, category);
        } else if (type != null) {
            products = productService.getProductsByType(type);
        } else if (category != null) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok(products);
    }

    // 상품 상세 정보
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // High 타입 전체 가져오기
    @GetMapping("/high")
    public ResponseEntity<List<Product>> getHighProducts() {
        return ResponseEntity.ok(productService.getProductsByType("high"));
    }

    // High 타입 상위 4개만
    @GetMapping("/high/top4")
    public ResponseEntity<List<Product>> getTop4HighProducts() {
        return ResponseEntity.ok(productService.getTop4HighProducts());
    }

    // best 타입 상품 리스트 (타입별 그룹)
    @GetMapping("/best-by-cate")
    public ResponseEntity<List<Product>> getBestProductsByCate(@RequestParam String cate) {
        return ResponseEntity.ok(productService.getBestProductsByCate(cate));
    }
    // medium 배너
    @GetMapping("/medium")
    public ResponseEntity<List<Product>> getMediumProducts() {
        return ResponseEntity.ok(productService.getProductsByType("medium"));
    }


    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("q") String keyword) {
        return productService.searchProducts(keyword);
    }
}