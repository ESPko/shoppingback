package com.example.shoppringback.repository;

import com.example.shoppringback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(String type);
    List<Product> findTop4ByTypeOrderByIdDesc(String type);
    List<Product> findByTypeAndCategory(String type, String category);
    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}

