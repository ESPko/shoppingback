package com.example.shoppringback.repository;

import com.example.shoppringback.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}