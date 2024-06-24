package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RoomInfoService roomInfoService;

    public List<Accommodation> findAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public int findMinPrice(List<RoomInfo> roomInfos) {
        return roomInfos.stream()
                .mapToInt(RoomInfo::getPrice)
                .min()
                .orElse(0);
    }

    public AccommodationDto convertToDto(Accommodation accommodation, int minPrice) {
        AccommodationDto dto = new AccommodationDto();
        dto.setId(accommodation.getId());
        dto.setAccommodationName(accommodation.getName());
        dto.setCategoryName(accommodation.getCategory().getName());
        if (accommodation.getGrade() != null) {
            dto.setGrade(accommodation.getGrade().getGrade());
        } else {
            dto.setGrade("");
        }
        dto.setAddress(accommodation.getAddress());
        dto.setLatitude(accommodation.getLatitude());
        dto.setLongitude(accommodation.getLongitude());
        dto.setMinPrice(minPrice);
        return dto;
    }

    public Accommodation findAccommodationById(Long accId) {
        return accommodationRepository.findById(accId).orElse(null);
    }

    public List<RoomInfo> findRoomInfosByAccommodationId(Long accommodationId) {
        return roomInfoService.findByAccommodationId(accommodationId);
    }

    public Page<Review> findReviewsByAccommodationId(Long accommodationId, Pageable pageable) {
        return reviewService.findReviewsByAccommodationId(accommodationId, pageable);
    }

    // 별점 평균 계산
    public int calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0;
        }

        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRate();
        }

        return (int) Math.ceil(totalRating / reviews.size());
    }

    public AccommodationDto getAccommodationDto(Long accId, Pageable pageable) {
        Accommodation accommodation = findAccommodationById(accId);

        if (accommodation == null) {
            return null;
        }

        List<RoomInfo> roomInfos = findRoomInfosByAccommodationId(accId);
        Page<Review> reviewPage = findReviewsByAccommodationId(accId, pageable);
        List<Review> allReviews = reviewService.findAllReviews();
        List<Review> reviews = reviewPage.getContent();
        int averageRating = calculateAverageRating(allReviews);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, reviewPage.getTotalPages());

        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setId(accommodation.getId());
        accommodationDto.setAccommodationName(accommodation.getName());
        accommodationDto.setCategoryName(accommodation.getCategory() != null ? accommodation.getCategory().getName() : "");
        accommodationDto.setGrade(accommodation.getGrade() != null ? accommodation.getGrade().getGrade() : "");
        accommodationDto.setAddress(accommodation.getAddress());
        accommodationDto.setLatitude(accommodation.getLatitude());
        accommodationDto.setLongitude(accommodation.getLongitude());
        accommodationDto.setMinPrice(findMinPrice(roomInfos));
        accommodationDto.setAverageRating(averageRating);
        accommodationDto.setReviews(reviews);
        accommodationDto.setRoomInfos(roomInfos);
        accommodationDto.setTotalReviews(reviewPage.getTotalElements());
        accommodationDto.setNowPage(nowPage);
        accommodationDto.setStartPage(startPage);
        accommodationDto.setEndPage(endPage);

        return accommodationDto;
    }

    public List<Review> findAllReviews(Long accId) {
        return reviewRepository.findByRoomInfoAccommodationIdOrderByIdDesc(accId);
    }
}
