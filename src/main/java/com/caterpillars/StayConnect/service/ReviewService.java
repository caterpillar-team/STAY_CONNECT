package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    // 리뷰 추가
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

        String username = user != null ? user.getUsername() : "Unknown";
        Long roomId = roomInfo.getId();
        Long accId = roomInfo.getAccommodation() != null ? roomInfo.getAccommodation().getId() : null;
        log.info("reviewServiceRoomId : " + roomId);
        log.info("reviewServiceAccId : " + accId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = review.getCreatedAt().format(formatter);

        try {
            // 리뷰 저장
            review = reviewRepository.save(review);
            // 리뷰id를 DTO에 설정
            dto.setReviewId(review.getId());
            log.info(
                    "Saving review: Review(id={}, user={}, roomInfo={}, accommodationId={}, contents={}, createdAt={}, rate={})",
                    review.getId(), username, roomId, accId, review.getContents(),
                    formattedCreatedAt, review.getRate());

        } catch (Exception e) {
            log.error("Error saving review", e);
            return false;
        }

        return review != null && reviewRepository.existsById(review.getId());
    }

    // 리뷰 수정
    @Transactional
    public Map<String, Object> updateReview(ReviewDto reviewDto) {
        Map<String, Object> result = new HashMap<>();
        Optional<Review> reviewOptional = reviewRepository.findById(reviewDto.getReviewId());
        if (reviewOptional.isEmpty()) {
            result.put("message", "리뷰를 찾을 수 없습니다.");
            return result;
        }
        Review review = reviewOptional.get();

        // 리뷰 내용 및 평점 수정
        review.setContents(reviewDto.getContents());
        review.setRate(reviewDto.getRate());
        // 리뷰 저장
        reviewRepository.save(review);
        return result;
    }

    // 리뷰 조회
    public ReviewDto getReviewDto(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        try {
            Review review = optionalReview.orElseThrow(NoSuchElementException::new);
            return ReviewDto.Of(review);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    // 리뷰 삭제
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.delete(review);
            return !reviewRepository.existsById(reviewId);
        } else {
            return false; // 삭제할 리뷰를 찾을 수 없음
        }
    }

    public RoomInfo findRoomInfoById(long roomInfoId) {
        return roomInfoRepository.findById(roomInfoId)
                .orElse(null);
    }


    public Page<Review> findReviewsByAccommodationId(Long accommodationId, Pageable pageable) {
        return reviewRepository.findByRoomInfoAccommodationId(accommodationId, pageable);
    }

    public List<Review> findAllReviews(Long accId) {
        return reviewRepository.findByRoomInfoAccommodationIdOrderByIdDesc(accId);
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findByRoomInfoAccommodationIdOrderByIdDesc();
    }
}