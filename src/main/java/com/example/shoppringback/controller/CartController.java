package com.example.shoppringback.controller;

import com.example.shoppringback.dto.*;
import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.repository.ProductRepository;
import com.example.shoppringback.service.CartService;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    // 장바구니 조회
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.notFound().build();  // 장바구니가 없으면 404 Not Found
        }

        // CartResponse 생성
        CartResponse cartResponse = new CartResponse(cart, productRepository);
        return ResponseEntity.ok(cartResponse);
    }

    // 상품 추가
    @PostMapping("/{userId}/item")
    public ResponseEntity<Void> addItem(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request
    ) {
        System.out.println("addItem called with productId = " + request.getProductId());
        System.out.println("quantity = " + request.getQuantity());
        System.out.println("selectedSize = " + request.getSelectedSize());
        cartService.addItemToCart(userId, request.getProductId(), request.getQuantity(), request.getSelectedSize());
        return ResponseEntity.ok().build();
    }

    // 수량 변경
    @PutMapping("/item/{cartItemId}/quantity")
    public ResponseEntity<Void> changeQuantity(
            @PathVariable Long cartItemId,
            @RequestBody ChangeQuantityRequest request
    ) {
        cartService.changeQuantity(cartItemId, request.getQuantityDiff());
        return ResponseEntity.ok().build();
    }

    // 옵션 변경
    @PutMapping("/item/{cartItemId}/size")
    public ResponseEntity<Void> changeSize(
            @PathVariable Long cartItemId,
            @RequestBody ChangeSizeRequest request
    ) {
        cartService.changeSize(cartItemId, request.getNewSize());
        return ResponseEntity.ok().build();
    }

    // 아이템 삭제
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
        return ResponseEntity.ok().build();
    }
}


