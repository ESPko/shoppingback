package com.example.shoppringback.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/oauth")
public class RestAPIController {
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @GetMapping("/kakao")
//    public ResponseEntity<?> kakaoCallback(@RequestParam String code){
//
//        try {
//            // 1. access_token 요청
//            String tokenUrl = "https://kauth.kakao.com/oauth/token";
//            HttpHeaders tokenHeaders = new HttpHeaders();
//            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            String tokenBody = "grant_type=authorization_code"
//                    + "&client_id=5a2cdb865eedde9c4592943301eec024"
//                    + "&redirect_uri=http://localhost:5173/auth/kakao/callback"
//                    + "&code=" + code;
//
//            HttpEntity<String> tokenRequest = new HttpEntity<>(tokenBody, tokenHeaders);
//            RestTemplate restTemplate = new RestTemplate();
//
//            ResponseEntity<String> tokenResponse = restTemplate.exchange(
//                    tokenUrl, HttpMethod.POST, tokenRequest, String.class);
//
//            String accessToken = objectMapper.readTree(tokenResponse.getBody())
//                    .get("access_token").asText();
//
//            // 2. 사용자 정보 요청
//            String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
//            HttpHeaders userHeaders = new HttpHeaders();
//            userHeaders.setBearerAuth(accessToken);
//            HttpEntity<?> userRequest = new HttpEntity<>(userHeaders);
//
//            ResponseEntity<String> userResponse = restTemplate.exchange(
//                    userInfoUrl, HttpMethod.GET, userRequest, String.class);
//
//            // 3. 그대로 JSON 응답
//            return ResponseEntity.ok(objectMapper.readTree(userResponse.getBody()));
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("카카오 로그인 실패: " + e.getMessage());
//        }
//    }




}
