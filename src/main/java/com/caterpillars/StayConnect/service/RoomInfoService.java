package com.caterpillars.StayConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

import lombok.RequiredArgsConstructor;

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
