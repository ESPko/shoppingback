package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    private String category;
    private String brand;
    private String color;
    private Short size;

    @Column(nullable = false)
    private Long price;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = ZonedDateTime.now();
        }
    }
}