package com.caterpillars.StayConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caterpillars.StayConnect.model.entities.Inquiry;
import com.caterpillars.StayConnect.service.InquiryService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @GetMapping("/clientChatPopup")
    public String showClientChatPopup(Model model, HttpSession session) {
        String roomId = (String) session.getAttribute("roomId");
        model.addAttribute("roomId", roomId);
        return "pages/clientChatPopup";
    }

    @GetMapping("/adminChatPopup")
    public String showAdminChatPopup(Model model, HttpSession session) {
        List<Inquiry> rooms = inquiryService.findAllInquiry();
        String roomId = (String) session.getAttribute("roomId");

        
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomId", roomId);
        return "pages/adminChatPopup";
    }

    @MessageMapping("/chat.sendMessage")
    public Inquiry sendMessage(Inquiry inquiry, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            inquiry.setSender(username);

        }


        Inquiry savedInquiry = inquiryService.saveInquiry(inquiry.getSender(), inquiry.getContents(), inquiry.getRoomId());

        messagingTemplate.convertAndSend("/sub/chatroom/" + inquiry.getRoomId(), savedInquiry);
        return savedInquiry;
    }

    //특정 채팅방 입장하는 메서드
    @GetMapping("/enter/{roomId}")
    public String enterChatRoom(@PathVariable String roomId, HttpSession session) {
        session.setAttribute("roomId", roomId);
        return "redirect:/chat/popup";
    }

    //채팅방 생성 메서드
    @GetMapping("/createRoom")
    @ResponseBody
    public ResponseEntity<Inquiry> createRoom(Authentication authentication) {
        String username = authentication != null ? authentication.getName() : "anonymous";
        Inquiry inquiry = inquiryService.createChatRoom(username);
        return ResponseEntity.ok(inquiry);
    }

    //모든 채팅방 조회 메서드
    @GetMapping("/rooms")
    @ResponseBody
    public List<Inquiry> getRooms() {
        return inquiryService.findAllInquiry();
    }


}
