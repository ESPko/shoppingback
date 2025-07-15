package com.example.shoppringback.service;

import com.example.shoppringback.dto.CartItemResponse;
import com.example.shoppringback.entity.Cart;
import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.entity.Product;
import com.example.shoppringback.repository.CartItemRepository;
import com.example.shoppringback.repository.CartRepository;
import com.example.shoppringback.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

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

    // 유저별 장바구니 조회 (상품 정보와 함께)
    public List<CartItemResponse> getCartItemsByUserId(Long userId) {
        // 유저 ID로 장바구니를 조회
        Cart cart = getCartByUserId(userId);

        // Cart에 있는 CartItem들을 조회하여 CartItemResponse로 변환
        return cart.getCartItems().stream()
                .map(item -> {
                    // CartItem에서 productId를 이용해 Product 정보를 가져옵니다.
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    // CartItem과 Product 정보를 바탕으로 CartItemResponse를 생성
                    return new CartItemResponse(item, product);
                })
                .collect(Collectors.toList());
    }


    // 장바구니 아이템 추가 (기존 코드 유지)
    public void addItemToCart(Long userId, Long productId, int quantity, String selectedSize) {
        // 사용자 장바구니를 가져옴
        Cart cart = getCartByUserId(userId);

        // 상품 정보 조회 (가격을 가져오려면 ProductRepository 필요)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 새로운 CartItem 생성
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .productId(productId)
                .quantity(quantity)
                .selected(true)  // 기본값
                .selectedSize(selectedSize)
                .build();

        // 상품 가격을 CartItem에 설정
        cartItem.setPriceFromProduct(product);

        // 장바구니 항목 저장
        cartItemRepository.save(cartItem);
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
