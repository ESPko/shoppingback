package com.example.shoppringback.service;

import com.example.shoppringback.dto.ProductResponseDto;
import com.example.shoppringback.dto.ProductTypeGroupDto;
import com.example.shoppringback.entity.Product;
import com.example.shoppringback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // ProductService.java
    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public List<Product> getTop4HighProducts() {
        return productRepository.findTop4ByTypeOrderByIdDesc("high");
    }

    public List<Product> getTop4BestProducts() {
        return productRepository.findTop4ByTypeOrderByIdDesc("best");
    }

    public List<ProductTypeGroupDto> getBestProductsGroupedByType() {
        List<Product> allProducts = productRepository.findAll();

        // 타입별 그룹핑 + DTO 변환
        Map<String, List<ProductResponseDto>> grouped = allProducts.stream()
                .collect(Collectors.groupingBy(
                        Product::getType,
                        Collectors.mapping(ProductResponseDto::new, Collectors.toList())
                ));

        List<ProductTypeGroupDto> result = new ArrayList<>();
        grouped.forEach((type, items) -> result.add(new ProductTypeGroupDto(type, items)));

        return result;
    }

    public List<Product> getBestProductsByCate(String cate) {
        return productRepository.findByTypeAndCategory("best", cate);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByTypeAndCategory(String type, String category) {
        return productRepository.findByTypeAndCategory(type, category);
    }

}
