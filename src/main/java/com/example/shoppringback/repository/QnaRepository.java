package com.example.shoppringback.repository;

import com.example.shoppringback.entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<QnA, Long> {
    // 추가 검색 기능은 여기에 정의
}

