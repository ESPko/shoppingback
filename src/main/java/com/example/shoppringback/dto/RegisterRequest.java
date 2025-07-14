package com.example.shoppringback.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class RegisterRequest {
    @JsonProperty("userId")
    private String userId;
    private String password;
    private String nickname;
    private String email;

    private String smsReceive;
    private String emailReceive;
    private String birthDate; // yyyy-MM-dd

    private Address address;

    private String phone;   // 일반전화
    private String mobile;  // 휴대전화

    @Data
    public static class Address {
        private String zipCode;
        private String address1;
        private String address2;
    }
}
