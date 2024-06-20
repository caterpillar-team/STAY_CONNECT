package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomInfoService {

    @Autowired
    private static RoomInfoRepository roomInfoRepository;

    public RoomInfoService(RoomInfoRepository roomInfoRepository) {
        this.roomInfoRepository = roomInfoRepository;
    }

    public static RoomInfo findById(Long id) {
        return roomInfoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정보입니다."));
    }
}
