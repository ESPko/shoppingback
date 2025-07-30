package com.example.shoppringback.controller;

import com.example.shoppringback.dto.OrderRequestDto;
import com.example.shoppringback.dto.OrderResponseDto;
import com.example.shoppringback.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequestDto orderRequest) {

        System.out.println("userId: " + userId);
        orderRequest.getItems().forEach(item ->
                System.out.println("productId=" + item.getProductId() + ", quantity=" + item.getQuantity()));

        OrderResponseDto responseDto = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 주문 단건 조회 (주문 ID로)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto responseDto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 유저의 전체 주문 리스트 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
