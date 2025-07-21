package com.example.shoppringback.dto;

import com.example.shoppringback.entity.Product;

// ProductResponseDto.java
public class ProductResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String infoImage;  // 상세 이미지 URL (프론트에서 product.image로 쓰면 됨)
    private String link;       // 링크가 DB에 없으면 빈 문자열로 처리

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.infoImage = product.getInfoImage();
        this.link = ""; // DB에 링크 컬럼 없으니 빈 문자열로 기본 처리
    }

    // getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getPrice() { return price; }
    public String getInfoImage() { return infoImage; }
    public String getLink() { return link; }
}
