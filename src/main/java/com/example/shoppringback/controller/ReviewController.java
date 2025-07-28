package com.example.shoppringback.controller;

import com.example.shoppringback.dto.PasswordRequest;
import com.example.shoppringback.entity.Review;
import com.example.shoppringback.repository.ReviewRepository;
import com.example.shoppringback.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 목록 조회
    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getAllReviews();
    }

    // 특정 리뷰 조회
    @GetMapping("/{id}")
    public Review getReview(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    // 리뷰 작성 (이미지 제외)
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        // 리뷰 저장
        return reviewService.createReview(review);
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, @RequestBody PasswordRequest passwordRequest) {
        // 클라이언트에서 받은 비밀번호
        String providedPassword = passwordRequest.getPassword();

        try {
            // 리뷰 조회
            Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."));

            // 비밀번호 검증
            if (providedPassword == null || providedPassword.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 입력하세요.");
            }

            if (!review.getPassword().equals(providedPassword)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 틀렸습니다.");
            }

            // 리뷰 삭제
            reviewRepository.delete(review);
            return ResponseEntity.ok("리뷰가 삭제되었습니다.");

        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
    // 리뷰 목록 조회 (페이징 처리)
    @GetMapping("/paged")
    public List<Review> getPagedReviews(@RequestParam int page, @RequestParam    int size) {
        return reviewService.getReviews(page, size);
    }
}
