//package com.websocket.be.config;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
//
//import java.security.Principal;
//import java.util.Map;
//
//public class CustomHandshakeHandler extends DefaultHandshakeHandler {
//
//    @Override
//    protected Principal determineUser(ServerHttpRequest request,
//                                      WebSocketHandler wsHandler,
//                                      Map<String, Object> attributes) {
//        // Tạo principal từ token hoặc giá trị trong session attributes
//        String token = (String) attributes.get("token");
//        if (token != null) {
//            // Tạo Principal từ token
//            return new StompPrincipal(token);
//        }
//
//        // Fallback nếu không có token
//        return new StompPrincipal("anonymous");
//    }
//}
