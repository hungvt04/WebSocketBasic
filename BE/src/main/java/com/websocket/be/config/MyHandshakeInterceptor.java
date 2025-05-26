//package com.websocket.be.config;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Cái này chỉ hoạt động với WebSocket thuần không đi kèm với STOMP
// *      Khi sử dụng STOMP thì phải implements ChannelInterceptor
// */
//public class MyHandshakeInterceptor implements HandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request,
//                                   ServerHttpResponse response,
//                                   WebSocketHandler wsHandler,
//                                   Map<String, Object> attributes) throws Exception {
//        // Lấy token từ header Authorization (ví dụ "Bearer <token>")
//        List<String> authHeaders = request.getHeaders().get("Authorization");
//        String token = null;
//
//        if (authHeaders != null && !authHeaders.isEmpty()) {
//            String authHeader = authHeaders.get(0);
//            if (authHeader.startsWith("Bearer ")) {
//                token = authHeader.substring(7);
//            }
//        }
//
//        if (token != null) {
//            // Lưu token vào attributes để có thể truy cập trong WebSocketHandler
//            System.out.println("TOKEN: " + token);
//            attributes.put("token", token);
//            return true; // Cho phép handshake tiếp tục
//        } else {
//            // Không có token, từ chối handshake
//            return false;
//        }
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request,
//                               ServerHttpResponse response,
//                               WebSocketHandler wsHandler,
//                               Exception exception) {
//
//    }
//
//}
