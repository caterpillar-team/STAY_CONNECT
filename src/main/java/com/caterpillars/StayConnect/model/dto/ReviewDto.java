package com.caterpillars.StayConnect.model.dto;

<<<<<<< Updated upstream
import com.caterpillars.StayConnect.model.entities.Review;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> Stashed changes

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

<<<<<<< Updated upstream
    private long reviewId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Contents are required")
    private String contents;
    @Min(value = 1, message = "Rate must be at least 1")
    @NotNull(message = "Rate is required")
=======
    private String contents;
>>>>>>> Stashed changes
    private int rate;
    private String createdAt;
    private long roomInfoId;
    private long accId;

    public static ReviewDto Of(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.username = review.getUser().getUsername();
        dto.contents = review.getContents();
        dto.rate = review.getRate();
        dto.createdAt = review.getCreatedAt() != null ? review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
        dto.roomInfoId = review.getRoomInfo().getId();
        dto.accId = review.getRoomInfo().getAccommodation().getId();
        return dto;
    }

}
