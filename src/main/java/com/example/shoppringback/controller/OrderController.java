package com.example.shoppringback.controller;

import com.example.shoppringback.dto.OrderRequestDto;
import com.example.shoppringback.entity.Order;
import com.example.shoppringback.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody OrderRequestDto orderRequest) {
        Order order = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(order);
    }

    // 필요하면 주문 조회 API도 추가 가능
}
