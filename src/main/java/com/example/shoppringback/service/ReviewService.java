package com.example.shoppringback.service;

import com.example.shoppringback.entity.Review;
import com.example.shoppringback.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 목록 조회
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 특정 리뷰 조회
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // 리뷰 생성
    public Review createReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());  // 생성 일자
        review.setUpdatedAt(LocalDateTime.now());  // 수정 일자도 처음엔 동일하게 설정
        return reviewRepository.save(review);
    }

    // 리뷰 수정
    public Review updateReview(Long id, Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            Review updatedReview = existingReview.get();
            updatedReview.setRating(review.getRating());
            updatedReview.setName(review.getName());
            updatedReview.setContent(review.getContent());
            updatedReview.setImages(review.getImages()); // 이미지 경로 업데이트
            updatedReview.setUpdatedAt(LocalDateTime.now());  // 수정 일자
            return reviewRepository.save(updatedReview);
        }
        return null;
    }

    // 리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    // 리뷰 목록 조회 (페이징)
    public List<Review> getReviews(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page는 0부터 시작하므로 -1을 해줘야 함
        return reviewRepository.findAll(pageable).getContent();
    }

    // 리뷰 요약 통계 조회 (averageRating, ratingCounts)
    public Map<String, Object> getReviewSummary() {
        List<Review> reviews = reviewRepository.findAll();

        Map<String, Object> summary = new HashMap<>();
        int totalCount = reviews.size();
        int totalRating = 0;
        Map<Integer, Integer> ratingCounts = new HashMap<>();
        for (Review review : reviews) {
            totalRating += review.getRating();
            ratingCounts.put(review.getRating(), ratingCounts.getOrDefault(review.getRating(), 0) + 1);
        }

        // 평균 별점 계산
        double averageRating = totalCount == 0 ? 0.0 : (double) totalRating / totalCount;

        summary.put("averageRating", averageRating);
        summary.put("ratingCounts", ratingCounts);
        summary.put("totalCount", totalCount);

        return summary;
    }

    // 리뷰 목록 조회 (페이징 처리)
    public Map<String, Object> getPagedReviews(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);  // page는 0부터 시작하므로 -1을 해줘야 함
        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviewPage.getContent()); // 현재 페이지의 리뷰 목록
        result.put("totalPages", reviewPage.getTotalPages()); // 총 페이지 수
        result.put("currentPage", page); // 현재 페이지 추가
        result.put("totalReviews", reviewPage.getTotalElements()); // 총 리뷰 개수

        return result;
    }
}
