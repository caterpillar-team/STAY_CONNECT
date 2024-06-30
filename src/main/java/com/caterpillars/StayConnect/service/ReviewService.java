package com.caterpillars.StayConnect.service;

// import com.caterpillars.StayConnect.model.entities.Review;
// import com.caterpillars.StayConnect.model.entities.RoomInfo;
// import com.caterpillars.StayConnect.model.repository.ReviewRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;

@Service
// @RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
//    private final ReviewRepository reviewRepository;

//    // 리뷰 저장
//    public void saveReview(String user_id, String roomInfo_id, String contents, int rate) {
//        // 사용자 ID와 방 정보 ID로 리뷰를 생성하고 저장
//        Review review = new Review();
//        review.setUser(user);
//        review.setRoomInfo(roomInfo);
//        review.setContents(contents);
//        review.setRate(rate);
//        reviewRepository.save(review);
//    }

//    // 리뷰 생성
//    public Long create(Review review) {
//        reviewRepository.save(review);
//        return review.getId();
//    }

//    // 리뷰 조회
//    @Transactional(readOnly = true)
//    public List<Review> findByRoomInfo(RoomInfo roomInfo) {
//        return reviewRepository.findByRoomInfo(roomInfo);
//    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
