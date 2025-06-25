package com.example.shoppringback.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "users") // user는 예약어일 수 있어서 users 추천
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider; // "kakao", "google" 등
    private String providerId; // 카카오에서 받은 고유 ID
    private String email;
    private String nickname;
    private String profileImage;

    private String phoneNumber;
    private String gender;       // "male", "female"
    private String ageRange;     // 예: "20~29"
    private String birthyear;    // 예: "1999"
    private String birthday;     // 예: "0610"
    private String birthdayType; // 예: "SOLAR"
    private boolean isLeapMonth; // 윤달 여부


    private String role = "USER"; // 기본 권한

    // 추가로 필요한 필드: 가입일자, 수정일자, 주소, 전화번호 등

    @Column(name = "created_at", nullable = false, length = 8)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    // getters and setters 생략
}
