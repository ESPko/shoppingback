package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")  // 'order'는 예약어라 'orders' 사용
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String receiverName;
    private String receiverPhone;
    private String zipCode;
    private String address1;
    private String address2;
    private String deliveryMessage;

    private String status;  // ex) "주문완료", "배송중", "배송완료"

    @CreationTimestamp // 자동 생성
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "주문완료";
        }
    }
}
