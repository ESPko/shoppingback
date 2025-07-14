package com.example.shoppringback.service;

import com.example.shoppringback.dto.RegisterRequest;
import com.example.shoppringback.entity.User;
import com.example.shoppringback.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.PathMatcher;

@Service
public class UserService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String userId, String rawPassword) {
        return userRepository.findByUserId(userId)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(null);
    }

    public boolean existsByUserId(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    // RegisterRequest 받아서 User로 변환 후 저장하는 메서드
    public User register(RegisterRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 암호화
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setProvider("local");

        user.setSmsReceive(request.getSmsReceive());
        user.setEmailReceive(request.getEmailReceive());
        user.setPhoneNumber(request.getPhone()); // 일반전화
        user.setMobile(request.getMobile());

        if (request.getBirthDate() != null && request.getBirthDate().length() >= 10) {
            user.setBirthyear(request.getBirthDate().substring(0, 4));     // "1990"
            user.setBirthday(request.getBirthDate().substring(5).replace("-", "")); // "1201"
        }

        if (request.getAddress() != null) {
            user.setZipCode(request.getAddress().getZipCode());
            user.setAddress1(request.getAddress().getAddress1());
            user.setAddress2(request.getAddress().getAddress2());
        }

        return userRepository.save(user);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
