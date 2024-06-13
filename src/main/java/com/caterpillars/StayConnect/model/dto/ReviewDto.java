package com.caterpillars.StayConnect.model.dto;

import com.caterpillars.StayConnect.model.entities.Review;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private long reviewId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Contents are required")
    private String contents;
    @Min(value = 1, message = "Rate must be at least 1")
    @NotNull(message = "Rate is required")
    private int rate;
    private String createdAt;
    private long roomInfoId;
    private long accId;

    public static ReviewDto Of(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.username = review.getUser().getUsername();
        dto.title = review.getTitle();
        dto.contents = review.getContents();
        dto.rate = review.getRate();
//        dto.createdAt = review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(review.getCreatedAt() != null) {
            dto.createdAt = review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            dto.createdAt = null;
        }
        dto.roomInfoId = review.getRoomInfo().getId();
        dto.accId = review.getRoomInfo().getAccommodation().getId();
        return dto;
    }

}
