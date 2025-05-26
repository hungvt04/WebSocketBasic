package com.websocket.be.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if(token.length() >= 4) {
                    String username = token.substring(token.length() - 4);
                    log.info("username: {}", username);
                    UserPrincipal principal = new UserPrincipal(username);
                    accessor.setUser(principal);
                }
            }
        }
        log.info("PreSend command: {}", accessor.getCommand());
        log.info("Authorization header: {}", accessor.getFirstNativeHeader("Authorization"));

        return message;
    }
}
