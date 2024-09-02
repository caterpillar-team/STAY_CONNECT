package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

  private List<User> users;
  private List<RoomInfo> roomInfos;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    users = userRepository.findAll();
    roomInfos = roomInfoRepository.findAll();

    final int TOTAL_ROOM_INFOS = 30 * 3;
    for (int i = 1; i <= TOTAL_ROOM_INFOS; i++) {
      User user = users.get((i - 1) % 10);
      RoomInfo roomInfo = roomInfos.get(i - 1);

      Review review1 = new Review();
      review1.setUser(user);
      review1.setRoomInfo(roomInfo);
      review1.setContents("Review comment for RoomInfo " + i);
      review1.setRate((int) (Math.random() * 5) + 1);
      review1.setCreatedAt(LocalDateTime.now());
      reviewRepository.save(review1);

      Review review2 = new Review();
      review2.setUser(user);
      review2.setRoomInfo(roomInfo);
      review2.setContents("Another review comment for RoomInfo " + i);
      review2.setRate((int) (Math.random() * 5) + 1);
      review2.setCreatedAt(LocalDateTime.now());
      reviewRepository.save(review2);
    }
  }
}
