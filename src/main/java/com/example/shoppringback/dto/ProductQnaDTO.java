package com.example.shoppringback.dto;


import com.example.shoppringback.entity.ProductQna;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQnaDTO {
    private Long id;
    private String title;
    private String name;
    private LocalDateTime date;
    private Integer hit;
    private int secret;
    private String content;
    private Long productId;     // product_id만 포함
    private String productName; // Product의 이름 필드가 있을 경우
    private String password;

    // 엔티티 -> DTO 변환 메서드
    public static ProductQnaDTO fromEntity(ProductQna qna) {
        return ProductQnaDTO.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .name(qna.getName())
                .date(qna.getDate() != null ? qna.getDate() : LocalDateTime.now())
                .hit(qna.getHit())
                .secret(qna.getSecret())
                .content(qna.getContent())
                .productId(qna.getProduct() != null ? qna.getProduct().getId() : null)
                .productName(qna.getProduct() != null ? qna.getProduct().getName() : null)
                .build();
    }
}
