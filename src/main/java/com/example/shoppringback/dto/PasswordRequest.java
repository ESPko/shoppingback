package com.example.shoppringback.dto;


public class PasswordRequest {
    private String password; // 비밀번호 필드

    // 기본 생성자
    public PasswordRequest() {}

    // 비밀번호 필드를 받는 생성자
    public PasswordRequest(String password) {
        this.password = password;
    }

    // getter와 setter 추가
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

