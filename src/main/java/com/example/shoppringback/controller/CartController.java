package com.example.shoppringback.controller;

import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
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

    // 장바구니 조회
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new CartResponse(cart));
    }

    // 상품 추가
    @PostMapping("/{userId}/item")
    public ResponseEntity<Void> addItem(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request
    ) {
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


    // DTO & Request 클래스들

    @Getter
    static class CartResponse {
        private Long id;
        private Long userId;
        private List<CartItemResponse> items;

        public CartResponse(Cart cart) {
            this.id = cart.getId();
            this.userId = cart.getUserId();
            this.items = cart.getCartItems().stream()
                    .map(CartItemResponse::new)
                    .toList();
        }
    }

    @Getter
    static class CartItemResponse {
        private Long id;
        private Long productId;
        private int quantity;
        private String selectedSize;
        private boolean selected;

        public CartItemResponse(CartItem item) {
            this.id = item.getId();
            this.productId = item.getProductId();
            this.quantity = item.getQuantity();
            this.selectedSize = item.getSelectedSize();
            this.selected = item.isSelected();
        }
    }

    @Getter
    static class AddItemRequest {
        private Long productId;
        private int quantity;
        private String selectedSize;
    }

    @Getter
    static class ChangeQuantityRequest {
        private int quantityDiff;
    }

    @Getter
    static class ChangeSizeRequest {
        private String newSize;
    }
}

