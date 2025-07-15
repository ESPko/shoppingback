package com.example.shoppringback.dto;

import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.entity.Product;
import com.example.shoppringback.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CartResponse {

    @JsonProperty("items")
    private List<CartItemResponse> items;

    // Cart와 ProductRepository를 받아서 CartItemResponse 리스트로 변환
    public CartResponse(Cart cart, ProductRepository productRepository) {
        this.items = cart.getCartItems().stream()
                .map(cartItem -> {
                    // productRepository로 Product를 조회
                    Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
                    // CartItem과 Product를 이용해 CartItemResponse를 생성
                    return new CartItemResponse(cartItem, product);
                })
                .collect(Collectors.toList());
    }
}
