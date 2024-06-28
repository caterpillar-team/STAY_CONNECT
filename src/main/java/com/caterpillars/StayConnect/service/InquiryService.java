package com.caterpillars.StayConnect.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caterpillars.StayConnect.model.entities.Inquiry;
import com.caterpillars.StayConnect.model.repository.InquiryRepository;

@Service
public class InquiryService {

    private ConcurrentMap<String, Inquiry> chatRooms = new ConcurrentHashMap<>();

    @Autowired
    private InquiryRepository inquiryRepository;

    private Map<String, Inquiry> lastMessage = new HashMap<>();

    public Inquiry createChatRoom(String sender) {
        String roomId = UUID.randomUUID().toString();
        Inquiry inquiry = Inquiry.builder()
                .sender(sender)
                .roomId(roomId)
                .build();
        chatRooms.put(roomId, inquiry);
        return inquiry;
    }


    @Transactional
    public Inquiry saveInquiry(String sender, String contents, String roomId) {
        Inquiry inquiry = Inquiry.builder()
            .sender(sender)
            .contents(contents)
            .createdAt(LocalDateTime.now())
            .build();
        inquiry.setRoomId(roomId); // 방 ID를 설정
        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        lastMessage.put(inquiry.getSender(), savedInquiry);
        return savedInquiry;
    }

    public List<Inquiry> findAllInquiry(){
        return inquiryRepository.findAll();
    }

    public Map<String, Inquiry> getLastMessages(){
        return lastMessage;
    }








}
