package com.caterpillars.StayConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caterpillars.StayConnect.model.entities.ChatRoom;
import com.caterpillars.StayConnect.model.entities.Inquiry;
import com.caterpillars.StayConnect.service.ChatRoomService;
import com.caterpillars.StayConnect.service.InquiryService;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private ChatRoomService chatRoomService;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/popup")
    public String showChatPopup(Model model, HttpSession session) {
        // 세션에서 방 ID 가져오기
        String roomId = (String) session.getAttribute("roomId");

        System.out.println("!!!!!!!!!!! " + roomId);

        model.addAttribute("roomId", roomId);
        return "pages/chatPopup";
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Inquiry sendMessage(Inquiry inquiry,Authentication authentication) {
        if(authentication !=null){
            String username = authentication.getName();
            inquiry.setSender(username);

        }

        Inquiry savedInquiry = inquiryService.savedInquiry(inquiry);

         // 메시지를 해당 채팅방 구독자들에게 전송
         messagingTemplate.convertAndSend("/sub/chatroom/" + inquiry.getRoomId(), inquiry);


         return savedInquiry;

    }

    @GetMapping("/enter/{roomId}")
    public String enterChatRoom(@PathVariable String roomId, HttpSession session) {
        // 세션에 방 ID 저장
        session.setAttribute("roomId", roomId);
        return "redirect:/chat/popup";
    }


    @GetMapping("/createRoom")
    @ResponseBody
    public ChatRoom createRoom() {
        return chatRoomService.createChatRoom();
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> getRooms() {
        return chatRoomService.findAllChatRooms();
    }





}
