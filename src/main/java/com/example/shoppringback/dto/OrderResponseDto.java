package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("receiverPhone")
    private String receiverPhone;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("deliveryMessage")
    private String deliveryMessage;

    @JsonProperty("status")
    private String status;

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @JsonProperty("totalPayAmount")
    private int totalPayAmount; // 필드 꼭 필요

    @JsonProperty("items")
    private List<OrderItemResponseDto> items;
}
