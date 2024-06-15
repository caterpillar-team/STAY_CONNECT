package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.ReviewDto;
import com.caterpillars.StayConnect.model.entities.RoomInfo;

public interface ReviewService {
    boolean addReview(ReviewDto dto, RoomInfo roomInfo);

    boolean deleteReview(Long reviewId);

    ReviewDto getReviewDto(Long reviewId);

    void updateReview(ReviewDto reviewDto);
}
