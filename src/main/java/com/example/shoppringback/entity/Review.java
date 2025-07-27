package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating; // 별점 (Integer로 변경 가능)

    private String name; // 작성자 (user로 변경 고려)

    private String content; // 리뷰 내용

    private LocalDateTime createdAt; // 작성일

    private String password;

    // images는 JSON 형식의 문자열로 저장되며, 이를 List로 변환하여 사용
    @Column(name = "images", columnDefinition = "TEXT")
    private String images; // 이미지 URL 저장 (JSON 형식으로)

    @Transient
    private List<String> imagesList;  // 실제로 사용되는 이미지는 List로 처리

    private LocalDateTime updatedAt; // 수정일 (필요시 추가)

    // 기본 생성자 추가 (JPA에서 필요)
    public Review() {
        // 기본 생성자는 객체를 초기화하기 위해 빈 값 또는 기본값을 설정
        this.images = "[]"; // 이미지 필드는 빈 배열로 기본값 설정
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 생성일자와 수정일자가 자동으로 세팅되도록 처리
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // JPA 엔티티를 로드한 후, images 문자열을 List로 변환하는 메서드
    @PostLoad
    public void postLoad() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // images JSON 문자열을 List<String>으로 변환
            this.imagesList = objectMapper.readValue(this.images, List.class);
        } catch (Exception e) {
            this.imagesList = null;  // 오류 발생 시 null 처리
        }
    }

    // 저장 전에 List를 JSON 문자열로 변환하여 images 필드에 저장
    public void setImagesList(List<String> imagesList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // List를 JSON 문자열로 변환하여 저장
            this.images = objectMapper.writeValueAsString(imagesList);
        } catch (Exception e) {
            this.images = "[]";  // 오류 발생 시 빈 배열 처리
        }
    }

    // 이미지 제외 생성자
    public Review(Integer rating, String name, String content, String password) {
        this.rating = rating;
        this.name = name;
        this.content = content;
        this.password = password;
        this.images = "[]"; // 이미지 필드는 빈 배열로 기본값 설정
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 이미지 포함 생성자
    public Review(Integer rating, String name, String content, List<String> imagesList) {
        this.rating = rating;
        this.name = name;
        this.content = content;
        setImagesList(imagesList); // 이미지를 List로 받아서 처리
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
