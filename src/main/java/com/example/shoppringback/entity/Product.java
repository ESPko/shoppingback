package com.example.shoppringback.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_idx")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private Integer price;

    @Column(name = "product_sale_price")
    private Integer salePrice;

    @Column(name = "product_size")
    private String size; // 사이즈 종류 (예: "S,M,L" 형태로 저장 가능)

    @Column(name = "product_color")
    private String color;


    @Column(name = "product_info")
    @JsonProperty("infoImage")
    private String infoImage; // 상세 설명 이미지 URL

    @Column(name = "product_cate")
    private String category;

    @Column(name = "product_type")
    private String type;
}

