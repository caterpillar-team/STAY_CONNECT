package com.caterpillars.StayConnect.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {



    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        // '/sub' 경로로 시작하는 메시지는 브로커가 처리하도록 설정
        config.enableSimpleBroker("/sub");
        // 애플리케이션 목적지 접두사는 '/app' 으로 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    // 웹소켓 엔드포인트 등록
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }


}
