package com.example.shoppringback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverProfile {
    private String resultcode;
    private String message;
    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

        private String age;
        private String gender;
        private String email;
        private String mobile;

        @JsonProperty("mobile_e164")
        private String mobileE164;

        private String name;
        private String birthday;
        private String birthyear;
    }
}
