package com.example.shoppringback.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "users") // user는 예약어라 users 추천
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;      // 일반 로그인용 아이디

    private String password;    // 비밀번호

    private String provider;    // "kakao", "google" 등
    private String providerId;  // 카카오에서 받은 고유 ID

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

    private String zipCode;
    private String address1;
    private String address2;

    private String mobile;
    private String smsReceive;
    private String emailReceive;

    private String role = "USER"; // 기본 권한

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
}
