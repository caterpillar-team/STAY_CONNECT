package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    // 별점 평균 계산
    public double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0;
        }

        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRate();
        }

        return totalRating / reviews.size();
    }

    // 리뷰 추가
    @Transactional(rollbackFor = Exception.class)
    public boolean addReview(ReviewDto dto, RoomInfo roomInfo) {
        if (roomInfo == null) {
            log.error("RoomInfo is null. Cannot proceed with adding review.");
            return false;
        }

        Review review = new Review();
        getReviewDto(dto.getReviewId());
        review.setTitle(dto.getTitle());
        review.setContents(dto.getContents());
        review.setRate(dto.getRate());
        review.setCreatedAt(dto.getCreatedAt() != null ? LocalDateTime.parse(dto.getCreatedAt()) : LocalDateTime.now());
        User user = userService.findByUsername(dto.getUsername());
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
            log.info("Saving review: Review(id={}, user={}, roomInfo={}, accommodationId={}, title={}, contents={}, createdAt={}, rate={})",
                    review.getId(), username, roomId, accId, review.getTitle(), review.getContents(), formattedCreatedAt, review.getRate());

        } catch (Exception e) {
            log.error("Error saving review", e);
            return false;
        }

        return review != null && reviewRepository.existsById(review.getId());
    }


    public boolean updateReview(ReviewDto dto) {
        Optional<Review> optionalReview = reviewRepository.findById(dto.getReviewId());

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setTitle(dto.getTitle());
            review.setContents(dto.getContents());
            review.setCreatedAt(LocalDateTime.now());
            User user = userService.findByUsername(dto.getUsername());
            review.setUser(user);

            review = reviewRepository.save(review);

            return review != null;
        } else {
            return false; // 업데이트할 리뷰를 찾을 수 없음
        }
    }


    public ReviewDto getReviewDto(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        try {
            Review review = optionalReview.orElseThrow(NoSuchElementException::new);
            // ReviewDto.Of() 메서드를 사용하여 Review 객체를 ReviewDto로 변환
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

}