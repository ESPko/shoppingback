package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Table(name = "qna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // product를 Product 엔티티와 연결 (ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")  // product_id 컬럼으로 매핑
    private Product product;

    private String title;

    private String name;

    private Date date;

    private Integer hit;

    private int secret;

    @Lob // Large Object Binary, 긴 텍스트 저장 가능
    private String content; // ✅ 게시글 본문 추가
}
