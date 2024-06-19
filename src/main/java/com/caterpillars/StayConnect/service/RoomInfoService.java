package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomInfoService {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    public List<RoomInfo> findByAccommodationId(long accId) {
        return roomInfoRepository.findByAccommodationId(accId);
    }

    public List<RoomInfo> findByAccommodation(Accommodation accommodation) {
        return roomInfoRepository.findByAccommodation(accommodation);
    }


}
