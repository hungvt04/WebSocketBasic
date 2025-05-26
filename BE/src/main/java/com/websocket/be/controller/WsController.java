package com.websocket.be.controller;

import ch.qos.logback.core.util.StringUtil;
import com.websocket.be.dto.ChatMessage;
import com.websocket.be.utils.GlobalVariable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WsController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String sendNotification(String message) {
        return message;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessages() {
        log.info("Hello from server at {}", System.currentTimeMillis());
        template.convertAndSend("/topic/messages", "Hello from server at " + System.currentTimeMillis());
    }

    @MessageMapping("/chat.send") // client gửi đến /app/chat.send
    @SendTo("/topic/messages") // server phát lại cho tất cả client tại đây
    public ChatMessage sendMessage(@Payload ChatMessage message, Principal principal) {
        message.setTimestamp(new Date().getTime());
        System.out.println(message.getContent());
        if (principal != null) {
            message.setSender(principal.getName());
        } else {
            message.setSender("anonymous");
        }
        return message;
    }
}
