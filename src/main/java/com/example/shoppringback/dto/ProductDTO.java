package com.example.shoppringback.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Integer price;
    private Integer salePrice;
    private String size;
    private String color;
    private String category;
    private String infoImage;

    // 생성자, getter/setter

    public ProductDTO(Long id, String name, Integer price, Integer salePrice,
                      String size, String color, String category, String infoImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.size = size;
        this.color = color;
        this.category = category;
        this.infoImage = infoImage;
    }

    // getter, setter 생략 (롬복 사용 가능)
}

