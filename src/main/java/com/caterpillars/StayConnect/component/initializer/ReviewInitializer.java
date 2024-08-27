package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Review;
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
    final int TOTAL_ROOM_INFOS = 30 * 3; // 총 30개의 숙소에 대해 3개의 방 정보가 있으므로
    for (int i = 1; i <= TOTAL_ROOM_INFOS; i++) {
      Review review1 = new Review();
      review1.setUser(userRepository.findById((long) ((i - 1) % 10 + 1))
          .orElseThrow(NoSuchElementException::new));
      review1.setRoomInfo(roomInfoRepository.findById((long) i)
          .orElseThrow(NoSuchElementException::new));
      review1.setContents("Review comment for RoomInfo " + i);
      review1.setRate((int) (Math.random() * 5) + 1); // 1에서 5 사이의 랜덤 평점
      review1.setCreatedAt(LocalDateTime.now());
      reviewRepository.save(review1);

      Review review2 = new Review();
      review2.setUser(userRepository.findById((long) ((i - 1) % 10 + 1))
          .orElseThrow(NoSuchElementException::new));
      review2.setRoomInfo(roomInfoRepository.findById((long) i)
          .orElseThrow(NoSuchElementException::new));
      review2.setContents("Another review comment for RoomInfo " + i);
      review2.setRate((int) (Math.random() * 5) + 1); // 1에서 5 사이의 랜덤 평점
      review2.setCreatedAt(LocalDateTime.now());
      reviewRepository.save(review2);
    }
  }
}
