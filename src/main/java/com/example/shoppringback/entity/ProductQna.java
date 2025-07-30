package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_qna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // Product 객체와 연결

    private String title;
    private String name;
    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;    private Integer hit;
    private int secret;
    private String password;

    @Lob // Large Object Binary, 긴 텍스트 저장 가능
    private String content; // 게시글 본문
}
