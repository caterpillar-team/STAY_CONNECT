package com.caterpillars.StayConnect.Handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.caterpillars.StayConnect.model.entities.Message;
import com.caterpillars.StayConnect.model.repository.MessageRepository;

//텍스트 메시지 처리 , 연결 관리, 메시지 DB 저장, 메시지 브로드캐스트

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler{

    private final List<WebSocketSession> sessions = new ArrayList<>();
    
    //DB에 메시지 저장하기 위해서
    @Autowired
    private MessageRepository messageRepository;

    //연결되면 호출됨
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);  //새로 연결된 세션을 session 리스트에 추가
        System.out.println("New session established: " + session.getId());
    }

    //누가 메시지를 보내면 호출
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();  //메시지의 내용을 변수에 저장

        System.out.println("Received message: " + payload);

        
        //DB에 메시지 저장 설정
        Message msg = new Message();
        msg.setSender("User");  //USER로 Sender 설정  
        msg.setContent(payload);        //내용 저장
        msg.setTimestamp(LocalDateTime.now());  //보낸 시간을 현재시간으로 저장
        messageRepository.save(msg);    //메시지를 DB에 저장

        System.out.println("Saved message to DB: " + msg);
        
        //세션을 반복
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {    //세션이 열려있는지 확인
                webSocketSession.sendMessage(message);  //메시지를 세션으로 전송
                System.out.println("Sent message to session: " + webSocketSession.getId());
            }
        }
    }

    //연결이 종료되고 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       //종료된 세션 제거
        sessions.remove(session);
        System.out.println("Session closed: " + session.getId());
    }
}
