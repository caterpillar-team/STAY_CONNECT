package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {

  private Long senderId;

  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  @Enumerated(EnumType.STRING)
  private MessageType type;

  public enum MessageType {
    TEXT, FILE, IMAGE, VIDEO
  }
}
