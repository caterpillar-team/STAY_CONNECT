package com.caterpillars.StayConnect.model.entities;

import java.time.LocalDateTime;

import com.caterpillars.StayConnect.model.dto.MessageDto.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "chat_room_id", nullable = false)
  private ChatRoom chatRoom;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private User sender;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
  private LocalDateTime timestamp;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MessageType type;
}
