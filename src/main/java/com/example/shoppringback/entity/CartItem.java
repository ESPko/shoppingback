package com.example.shoppringback.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("product_id")
    private Long productId; // 상품 ID만 저장 (필요시 Product 엔티티 매핑 가능)

    private int quantity;

    private String selectedSize;

    @Builder.Default
    private boolean selected = true;  // @Builder.Default 추가

    private int price;  // 상품 가격 추가

    // 가격을 가져오는 메서드 (상품 가격을 설정하는 메서드)
    public void setPriceFromProduct(Product product) {
        this.price = product.getPrice(); // Product 엔티티에서 가격을 가져옴
    }
}
