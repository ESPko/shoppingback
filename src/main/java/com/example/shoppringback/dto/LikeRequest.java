package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeRequest {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("productId")
    private Long productId;

    public LikeRequest() {
    }

    public LikeRequest(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
