package com.example.shoppringback.service;


import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.repository.CartItemRepository;
import com.example.shoppringback.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // 유저별 장바구니 조회 (없으면 생성)
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .userId(userId)
                            .build();
                    return cartRepository.save(cart);
                });
    }

    // 장바구니에 상품 추가
    public void addItemToCart(Long userId, Long productId, int quantity, String size) {
        Cart cart = getCartByUserId(userId);

        // 기존에 같은 상품 + 사이즈가 있는지 확인
        CartItem existingItem = cart.getCartItems().stream()
                .filter(i -> i.getProductId().equals(productId) && i.getSelectedSize().equals(size))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .productId(productId)
                    .quantity(quantity)
                    .selectedSize(size)
                    .selected(true)
                    .build();
            cart.getCartItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    // 수량 변경 (증감)
    public void changeQuantity(Long cartItemId, int quantityDiff) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found"));

        int newQty = item.getQuantity() + quantityDiff;
        if (newQty <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(newQty);
            cartItemRepository.save(item);
        }
    }

    // 옵션(사이즈) 변경
    public void changeSize(Long cartItemId, String newSize) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found"));

        item.setSelectedSize(newSize);
        cartItemRepository.save(item);
    }

    // 항목 삭제
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}

