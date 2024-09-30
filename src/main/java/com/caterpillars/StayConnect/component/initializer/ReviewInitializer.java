package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

@Order(6)
@Component
public class ReviewInitializer implements CommandLineRunner {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoomInfoRepository roomInfoRepository;

  @Override
  public void run(String... args) throws Exception {
    if (reviewRepository.count() > 0) {
      return; // 이미 리뷰가 존재하면 초기화하지 않음
    }

    List<User> users = userRepository.findAll();
    List<RoomInfo> roomInfos = roomInfoRepository.findAll();

    for (User user : users) {
      for (RoomInfo roomInfo : roomInfos) {
        Review review = Review.builder()
            .user(user)
            .roomInfo(roomInfo)
            .contents("Sample review content for room " + roomInfo.getId())
            .createdAt(LocalDateTime.now())
            .rate((int) (Math.random() * 5) + 1) // Ensure rate is between 1 and 5
            .build();
        reviewRepository.save(review);
      }
    }
  }
}
