package com.example.shoppringback.controller;

import com.example.shoppringback.dto.LoginRequest;
import com.example.shoppringback.dto.RegisterRequest;
import com.example.shoppringback.entity.User;
import com.example.shoppringback.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        System.out.println("로그인 시도: userId = " + request.getUserId());

        User user = userService.userRepository.findByUserId(request.getUserId()).orElse(null);
        if (user == null) {
            System.out.println("아이디가 존재하지 않음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        boolean passwordMatches = userService.getPasswordEncoder().matches(request.getPassword(), user.getPassword());
        System.out.println("비밀번호 매칭 결과: " + passwordMatches);

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        session.setAttribute("user", user);

        // 유저 정보 반환
        return ResponseEntity.ok(user);
    }




    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("RegisterRequest userId = " + request.getUserId());
        System.out.println("RegisterRequest = " + request);


        if (userService.existsByUserId(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }

        try {
            userService.register(request);
            System.out.println("회원가입 성공, userId = " + request.getUserId());
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
