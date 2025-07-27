package com.example.shoppringback.repository;

import com.example.shoppringback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(String type);
    List<Product> findTop4ByTypeOrderByIdDesc(String type);
    List<Product> findByTypeAndCategory(String type, String category);
    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    // 기존 메서드도 있겠지만, 대소문자 구분 없이 검색하기 위해 추가
    @Query("SELECT p FROM Product p WHERE p.type = :type AND LOWER(p.category) = LOWER(:category)")
    List<Product> findByTypeAndCategoryIgnoreCase(@Param("type") String type, @Param("category") String category);

}

