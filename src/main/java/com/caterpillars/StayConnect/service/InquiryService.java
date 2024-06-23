package com.caterpillars.StayConnect.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.Inquiry;
import com.caterpillars.StayConnect.model.repository.ChatRoomRepository;
import com.caterpillars.StayConnect.model.repository.InquiryRepository;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private Map<String, Inquiry> lastMessages = new ConcurrentHashMap<>();


    public Inquiry savedInquiry(Inquiry inquiry){

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        lastMessages.put(inquiry.getSender(), savedInquiry);
        return savedInquiry;
    }

    public List<Inquiry> findAllInquiry(){
        return inquiryRepository.findAll();
    }

    public Map<String, Inquiry> getLastMessages(){
        return lastMessages;
    }








}
