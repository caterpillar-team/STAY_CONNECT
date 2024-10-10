package com.caterpillars.StayConnect.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean addReview(ReviewDto dto, RoomInfo roomInfo) {
        if (roomInfo == null) {
            log.error("RoomInfo is null. Cannot proceed with adding review.");
            return false;
        }

        Review review = new Review();
        getReviewDto(dto.getReviewId());
        review.setContents(dto.getContents());
        review.setRate(dto.getRate());
        review.setCreatedAt(dto.getCreatedAt() != null ? LocalDateTime.parse(dto.getCreatedAt()) : LocalDateTime.now());
        User user = userRepository.findByUsername(dto.getUsername()).orElseGet(null);
        review.setUser(user);
        review.setRoomInfo(roomInfo);

        try {
            review = reviewRepository.save(review);
            dto.setReviewId(review.getId());
        } catch (Exception e) {
            log.error("Error saving review", e);
            return false;
        }

        return review != null && reviewRepository.existsById(review.getId());
    }

    @Transactional
    public Map<String, Object> updateReview(ReviewDto reviewDto) {
        Map<String, Object> result = new HashMap<>();
        Optional<Review> reviewOptional = reviewRepository.findById(reviewDto.getReviewId());
        if (reviewOptional.isEmpty()) {
            result.put("message", "리뷰를 찾을 수 없습니다.");
            return result;
        }
        Review review = reviewOptional.get();

        review.setContents(reviewDto.getContents());
        review.setRate(reviewDto.getRate());

        reviewRepository.save(review);
        return result;
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewDto(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        try {
            Review review = optionalReview.orElseThrow(NoSuchElementException::new);
            return ReviewDto.Of(review);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.delete(review);
            return !reviewRepository.existsById(reviewId);
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public RoomInfo findRoomInfoById(long roomInfoId) {
        return roomInfoRepository.findById(roomInfoId)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<Review> findReviewsByAccommodationId(Long accommodationId, Pageable pageable) {
        return reviewRepository.findByRoomInfoAccommodationId(accommodationId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Review> findAllReviews(Long accId) {
        return reviewRepository.findByRoomInfoAccommodationIdOrderByIdDesc(accId);
    }

    @Transactional(readOnly = true)
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }
}