package com.example.shoppringback.controller;

import com.example.shoppringback.dto.*;
import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.entity.Product;
import com.example.shoppringback.repository.ProductRepository;
import com.example.shoppringback.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")  // 기본 경로 설정
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

    // 상품 추가 (POST)
    @PostMapping("/{userId}/item")
    public ResponseEntity<CartItemResponse> addItem(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request
    ) {
        System.out.println("Request Body: " + request);

        // 필수 값 체크
        if (request.getProductId() == null || request.getQuantity() <= 0 || request.getSelectedSize() == null) {
            return ResponseEntity.badRequest().body(null);  // 잘못된 요청 처리
        }

        System.out.println("addItem called with productId = " + request.getProductId());
        System.out.println("quantity = " + request.getQuantity());
        System.out.println("selectedSize = " + request.getSelectedSize());

        // 장바구니에 아이템 추가 서비스 호출
        CartItem cartItem = cartService.addItemToCart(userId, request.getProductId(), request.getQuantity(), request.getSelectedSize());

        // 상품 정보를 사용해 CartItemResponse 생성
        Product product = productRepository.findById(cartItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // CartItemResponse 생성
        CartItemResponse response = new CartItemResponse(cartItem, product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 추가된 아이템 반환
    }




    // 수량 변경 (PUT)
    @PutMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<CartItemResponse> changeQuantity(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestBody ChangeQuantityRequest request
    ) {
        CartItemResponse updatedItem = cartService.changeQuantity(cartItemId, request.getQuantityDiff());

        if (updatedItem == null) {
            return ResponseEntity.noContent().build();  // 수량이 0 이하일 경우 삭제된 항목 처리
        }

        return ResponseEntity.ok(updatedItem);  // 변경된 아이템 반환
    }


    // 사이즈 변경 (PUT)
    @PutMapping("/{userId}/item/{cartItemId}/size")
    public ResponseEntity<CartItemResponse> changeSize(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestBody ChangeSizeRequest request
    ) {
        // 사이즈 변경 처리 후 변경된 CartItemResponse 반환
        CartItemResponse updatedItem = cartService.changeSize(cartItemId, request.getNewSize());

        // 변경된 CartItemResponse만 응답으로 반환
        return ResponseEntity.ok(updatedItem);
    }


    // 아이템 삭제 (DELETE)
    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) {
        cartService.removeItem(cartItemId);
        return ResponseEntity.ok().build();
    }
}
