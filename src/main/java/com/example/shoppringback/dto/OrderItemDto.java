package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("selectedSize")
    private String selectedSize;
}
