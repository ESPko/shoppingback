package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeSizeRequest {

    @JsonProperty("newSize")
    private String newSize;
}
