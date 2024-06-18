package com.caterpillars.StayConnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

@Service
public class RoomInfoService {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    public RoomInfoService(RoomInfoRepository roomInfoRepository) {
        this.roomInfoRepository = roomInfoRepository;
    }

    public RoomInfo findById(Long id) {
        Optional<RoomInfo> roomInfo = roomInfoRepository.findById(id);
        return roomInfo.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정보입니다."));
    }
}
