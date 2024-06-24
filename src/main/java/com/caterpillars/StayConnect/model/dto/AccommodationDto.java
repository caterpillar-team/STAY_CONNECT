package com.caterpillars.StayConnect.model.dto;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AccommodationDto {

    private Long id;
    private String accommodationName;
    private String categoryName;
    private String grade = "";
    private int minPrice;
    private int averageRating;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<Review> reviews;
    private List<RoomInfo> roomInfos;
    private long totalReviews;
    private int nowPage;
    private int startPage;
    private int endPage;

}
