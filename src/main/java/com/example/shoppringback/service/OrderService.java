package com.example.shoppringback.service;

import com.example.shoppringback.dto.*;
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

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(product.getPrice())
                            .selectedSize(itemDto.getSelectedSize())
                            .build();
                }).collect(Collectors.toList());

        if (orderItems.isEmpty()) {
            throw new RuntimeException("No items to order");
        }

        Order order = Order.builder()
                .user(user)
                .receiverName(orderRequest.getReceiverName())
                .receiverPhone(orderRequest.getReceiverPhone())
                .zipCode(orderRequest.getZipCode())
                .address1(orderRequest.getAddress1())
                .address2(orderRequest.getAddress2())
                .deliveryMessage(orderRequest.getDeliveryMessage())
                .status("주문접수") // 기본 주문 상태 예시
                .build();

        order.setOrderItems(orderItems);
        orderItems.forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return convertToResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponseDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertToResponseDto(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(item -> OrderItemResponseDto.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .selectedSize(item.getSelectedSize())
                        .build())
                .collect(Collectors.toList());

        int totalPayAmount = order.getOrderItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .zipCode(order.getZipCode())
                .address1(order.getAddress1())
                .address2(order.getAddress2())
                .deliveryMessage(order.getDeliveryMessage())
                .status(order.getStatus())
                .orderDate(order.getCreatedAt())
                .totalPayAmount(totalPayAmount)  // 총 결제금액 포함
                .items(itemDtos)
                .build();
    }
}
