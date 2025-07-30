package com.example.shoppringback.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private String selectedSize;

}
