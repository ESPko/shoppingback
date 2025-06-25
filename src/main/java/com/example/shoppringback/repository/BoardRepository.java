package com.example.shoppringback.repository;

import com.example.shoppringback.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 추가 검색 기능은 여기에 정의
}

