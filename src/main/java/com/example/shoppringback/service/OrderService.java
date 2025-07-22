package com.example.shoppringback.service;

import com.example.shoppringback.dto.OrderRequestDto;
import com.example.shoppringback.entity.*;
import com.example.shoppringback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(Long userId, OrderRequestDto orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> selectedItems = cart.getCartItems().stream()
                .filter(CartItem::isSelected)
                .collect(Collectors.toList());

        if(selectedItems.isEmpty()) {
            throw new RuntimeException("No selected items to order");
        }

        Order order = Order.builder()
                .user(user)
                .receiverName(orderRequest.getReceiverName())
                .receiverPhone(orderRequest.getReceiverPhone())
                .zipCode(orderRequest.getZipCode())
                .address1(orderRequest.getAddress1())
                .address2(orderRequest.getAddress2())
                .deliveryMessage(orderRequest.getDeliveryMessage())
                .build();

        List<OrderItem> orderItems = selectedItems.stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .selectedSize(cartItem.getSelectedSize())
                    .build();
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // 선택된 장바구니 아이템 삭제
        cart.getCartItems().removeIf(CartItem::isSelected);
        cartRepository.save(cart);

        return savedOrder;
    }
}
