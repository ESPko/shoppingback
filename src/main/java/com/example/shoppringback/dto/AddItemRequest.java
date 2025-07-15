package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
    public class AddItemRequest {
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("selectedSize")
    private String selectedSize;
    }

