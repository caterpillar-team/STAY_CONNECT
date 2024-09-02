package com.caterpillars.StayConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

@Service
public class RoomInfoService {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Transactional(readOnly = true)
    public List<RoomInfo> findByAccommodationId(Long accId) {
        return roomInfoRepository.findByAccommodationId(accId);
    }

    @Transactional(readOnly = true)
    public RoomInfo findById(Long id) {
        return roomInfoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 정보입니다."));
    }
}
