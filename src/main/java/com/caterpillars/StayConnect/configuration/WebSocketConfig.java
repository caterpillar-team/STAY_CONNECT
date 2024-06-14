package com.caterpillars.StayConnect.configuration;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.caterpillars.StayConnect.Handler.ChatWebSocketHandler;

@Configuration  //Bean 메서드 정의할 예정
@EnableWebSocket    //웹 소캣 활성화
public class WebSocketConfig implements WebSocketConfigurer{
    

    //웹소캣 핸들러 등록
    //핸들러 설정할 때 호출
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //핸들러 빈을 /chat 엔드포인트에 등록
        // /chat 경로로 들어오는 웹소캣 연결 처리
        registry.addHandler(chatWebSocketHandler(), "/chat")    
                .setAllowedOrigins("*");    //모든 도메인에서 오는 요청 허용
    }

    //메시지 처리 핸들러 , 통신 담당
    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }
}
