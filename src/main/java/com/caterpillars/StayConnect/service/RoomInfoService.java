package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomInfoService {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    public Optional<RoomInfo> findById(Long id) {
        return roomInfoRepository.findById(id);
    }
}
