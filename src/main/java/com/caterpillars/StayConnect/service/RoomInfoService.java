package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomInfoService {

    private RoomInfoRepository roomInfoRepository;

    @Autowired
    public RoomInfoService(RoomInfoRepository roomInfoRepository) {
        this.roomInfoRepository = roomInfoRepository;
    }

    public RoomInfo findById(Long id) {
        Optional<RoomInfo> roomInfo = roomInfoRepository.findById(id);
        return roomInfo.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정보입니다."));
    }
}
