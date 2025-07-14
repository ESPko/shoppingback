package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Long productId; // 상품 ID만 저장 (필요시 Product 엔티티 매핑 가능)

    private int quantity;

    private String selectedSize;

    private boolean selected = true;
}

