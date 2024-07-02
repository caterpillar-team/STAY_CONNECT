package com.caterpillars.StayConnect.component.initializer;

<<<<<<< Updated upstream
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
=======
>>>>>>> Stashed changes

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;
<<<<<<< Updated upstream

@Profile("dev")
@Order(5)
@Component
public class TestReviewDataInitializer implements CommandLineRunner {

        @Autowired
        private ReviewRepository reviewRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AccommodationRepository accommodationRepository;

        @Autowired
        private RoomInfoRepository roomInfoRepository;

        @Override
        public void run(String... args) throws Exception {

                Accommodation acc1 = accommodationRepository
                                .findByNameAndAddress("Test Hotel Paradise", "경상남도 진주시 장대로5번길 3 (장대동)")
                                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));

                List<RoomInfo> roomInfos = roomInfoRepository.findByAccommodation(acc1);
                if (roomInfos.isEmpty()) {
                        throw new IllegalArgumentException("No RoomInfo found for the accommodation");
                }
                RoomInfo room1 = roomInfos.get(0);

                User user = userRepository.findByUsername("user")
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                Review review1 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다1")
                                .createdAt(LocalDateTime.now())
                                .rate(4)
                                .build();
                Review review2 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다2")
                                .createdAt(LocalDateTime.now())
                                .rate(1)
                                .build();
                Review review3 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다3")
                                .createdAt(LocalDateTime.now())
                                .rate(2)
                                .build();
                Review review4 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다4")
                                .createdAt(LocalDateTime.now())
                                .rate(5)
                                .build();
                Review review5 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다5")
                                .createdAt(LocalDateTime.now())
                                .rate(1)
                                .build();
                Review review6 = Review.builder()
                                .user(user)
                                .roomInfo(room1)
                                .contents("좋았습니다6")
                                .createdAt(LocalDateTime.now())
                                .rate(3)
                                .build();

                reviewRepository.save(review1);
                reviewRepository.save(review2);
                reviewRepository.save(review3);
                reviewRepository.save(review4);
                reviewRepository.save(review5);
                reviewRepository.save(review6);
        }
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(5)
public class TestReviewDataInitializer implements CommandLineRunner {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Override
    public void run(String... args) throws Exception {

        Accommodation acc1 = accommodationRepository.findByNameAndAddress("Test Hotel Paradise", "경상남도 진주시 장대로5번길 3 (장대동)")
                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));

        List<RoomInfo> roomInfos = roomInfoRepository.findByAccommodation(acc1);
        if (roomInfos.isEmpty()) {
            throw new IllegalArgumentException("No RoomInfo found for the accommodation");
        }
        RoomInfo room1 = roomInfos.get(0);

        User user = userRepository.findByUsername("user").orElseThrow(() -> new IllegalArgumentException("User not found"));


    Review review1 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다1")
            .rate(4)
            .build();
    Review review2 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다2")
            .rate(1)
            .build();
    Review review3 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다3")
            .rate(2)
            .build();
    Review review4 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다4")
            .rate(5)
            .build();
    Review review5 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다5")
            .rate(1)
            .build();
    Review review6 = Review.builder()
            .user(user)
            .roomInfo(room1)
            .contents("좋았습니다6")
            .rate(3)
            .build();


    reviewRepository.save(review1);
    reviewRepository.save(review2);
    reviewRepository.save(review3);
    reviewRepository.save(review4);
    reviewRepository.save(review5);
    reviewRepository.save(review6);
  }
>>>>>>> Stashed changes
}