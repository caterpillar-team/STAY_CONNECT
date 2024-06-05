//package com.caterpillars.StayConnect.service;
//
//
//import com.caterpillars.StayConnect.model.dto.ReviewDto;
//import com.caterpillars.StayConnect.model.entities.Review;
//import com.caterpillars.StayConnect.model.repository.ReviewRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//public class ReviewServiceImpl {
//
//    private final ReviewRepository reviewRepository;
//
//    public ReviewDto convertToDto(Review review) {
//        ReviewDto reviewDto = new ReviewDto();
//        reviewDto.setId(review.getId());
//        reviewDto.setUser(review.getUser());
//        reviewDto.setRoomInfo(review.getRoomInfo());
//        reviewDto.setContents(review.getContents());
//        reviewDto.setCreatedAt(review.getCreatedAt());
//        reviewDto.setRate(review.getRate());
//        return reviewDto;
//    }
//
//}
