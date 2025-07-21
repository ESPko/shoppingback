package com.example.shoppringback.dto;

// ProductTypeGroupDto.java
import java.util.List;

public class ProductTypeGroupDto {
    private String type; // Shoes, Clothing, Acc., Sports
    private List<ProductResponseDto> items;

    public ProductTypeGroupDto(String type, List<ProductResponseDto> items) {
        this.type = type;
        this.items = items;
    }

    // getters
    public String getType() { return type; }
    public List<ProductResponseDto> getItems() { return items; }
}
