package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeQuantityRequest {

    @JsonProperty("quantityDiff")
    private int quantityDiff;
}
