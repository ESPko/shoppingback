package com.example.shoppringback.controller;


import com.example.shoppringback.component.OAuthClient;
import com.example.shoppringback.dto.KakaoProfile;
import com.example.shoppringback.dto.NaverProfile;
import com.example.shoppringback.entity.User;
import com.example.shoppringback.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    private final OAuthClient oAuthClient;

    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        try{
            // 1. 액세스 토큰 요청
            String accessToken = oAuthClient.getKakaoAccessToken(code);
            // 2. 사용자 정보 요청
            KakaoProfile profile = oAuthClient.getKakaoUserProfile(accessToken);
            // 3. DB 저장 또는 조회
            User user = oAuthService.saveOrGetKakaoUser(profile);

            // 4. jwt 생략

            // 5. 사용자 정보 반환
            return ResponseEntity.ok(Map.of(
                    "user", Map.of(
                            "id", user.getId(),
                            "email", user.getEmail(),
                            "nickname", user.getNickname(),
                            "profileImage", user.getProfileImage(),
                            "createdAt", user.getCreatedAt()
                    )
            ));

        }catch(Exception e){
            return ResponseEntity.status(500).body(Map.of("error", "로그인 실패", "message", e.getMessage()));
        }
    }


    @GetMapping("/naver")
    public ResponseEntity<?> naverLogin(@RequestParam String code, @RequestParam String state) {
        try {
            // 1. 액세스 토큰 요청
            String accessToken = oAuthClient.getNaverAccessToken(code, state);
            // 2. 사용자 정보 요청
            NaverProfile profile = oAuthClient.getNaverUserProfile(accessToken);
            // 3. DB 저장 또는 조회
            User user = oAuthService.saveOrGetNaverUser(profile);
            // 4. jwt 생략
            // 5. 사용자 정보 반환
            return ResponseEntity.ok(Map.of(
                    "user", Map.of(
                            "id", user.getId(),
                            "email", user.getEmail(),
                            "nickname", user.getNickname(),
                            "profileImage", user.getProfileImage(),
                            "createdAt", user.getCreatedAt()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "로그인 실패", "message", e.getMessage()));
        }
    }

}
