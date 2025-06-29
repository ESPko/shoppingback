package com.example.shoppringback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String name;

    private Date date;

}
