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

    public List<RoomInfo> findByAccommodationId(Long accId) {
        return roomInfoRepository.findByAccommodationId(accId);
    }

    public RoomInfoService(RoomInfoRepository roomInfoRepository) {
        this.roomInfoRepository = roomInfoRepository;
    }

    public RoomInfo findById(Long id) {
        return roomInfoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정보입니다."));
    }

}
