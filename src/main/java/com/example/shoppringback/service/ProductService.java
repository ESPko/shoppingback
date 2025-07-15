package com.example.shoppringback.service;

import com.example.shoppringback.entity.Product;
import com.example.shoppringback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 전체 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 상세 조회
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
