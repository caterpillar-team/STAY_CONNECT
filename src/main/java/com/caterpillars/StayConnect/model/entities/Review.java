package com.caterpillars.StayConnect.model.entities;


<<<<<<< Updated upstream
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
=======
import jakarta.persistence.*;
>>>>>>> Stashed changes
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
@Data
=======
import java.time.LocalDateTime;


@Getter
@Setter
>>>>>>> Stashed changes
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "roomInfo_id", nullable = false)
    private RoomInfo roomInfo;

    private String contents;

<<<<<<< Updated upstream
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    private int rate;

=======
  private LocalDateTime createdAt;
//  @PrePersist
//  protected void onCreate() {
//    this.createdAt = LocalDateTime.now();
//  }
  private int rate;
>>>>>>> Stashed changes
}
