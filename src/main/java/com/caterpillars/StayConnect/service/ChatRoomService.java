package com.caterpillars.StayConnect.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.ChatRoom;
import com.caterpillars.StayConnect.model.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    public void saveChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    public ChatRoom createChatRoom() {
        ChatRoom chatRoom = new ChatRoom();
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> findAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    private Map<String, String> sessionRoomIdMap = new ConcurrentHashMap<>();

    public String getRoomIdForSession(String sessionId) {
        return sessionRoomIdMap.get(sessionId);
    }

    public void setRoomIdForSession(String sessionId, String roomId) {
        sessionRoomIdMap.put(sessionId, roomId);
    }
}
