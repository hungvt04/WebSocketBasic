// Ví dụ sử dụng SockJS và STOMP với React
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

class WebSocketService {
  constructor() {
    this.stompClient = null;
    this.subscriptions = new Map();
  }

  // Kết nối đến WebSocket server với token xác thực
  connect(token, onConnected, onError) {
    // Thêm token vào query parameter
    const socket = new SockJS(`http://localhost:8080/ws?token=${token}`);
    this.stompClient = Stomp.over(socket);
    
    // Tùy chọn: tắt log của STOMP
    this.stompClient.debug = null;
    
    const headers = {};
    // Có thể thêm token vào header nếu cần
    // headers['Authorization'] = `Bearer ${token}`;
    
    this.stompClient.connect(
      headers,
      () => {
        console.log('WebSocket kết nối thành công');
        if (onConnected) onConnected();
      },
      (error) => {
        console.error('Lỗi kết nối:', error);
        if (onError) onError(error);
      }
    );
  }

  // Đăng ký nhận tin nhắn
  subscribe(destination, callback) {
    if (!this.stompClient || !this.stompClient.connected) {
      console.error('WebSocket chưa kết nối!');
      return;
    }
    
    const subscription = this.stompClient.subscribe(destination, (message) => {
      const data = JSON.parse(message.body);
      callback(data);
    });
    
    this.subscriptions.set(destination, subscription);
    return subscription;
  }

  // Gửi tin nhắn
  sendMessage(destination, message) {
    if (!this.stompClient || !this.stompClient.connected) {
      console.error('WebSocket chưa kết nối!');
      return;
    }
    
    this.stompClient.send(destination, {}, JSON.stringify(message));
  }

  // Hủy đăng ký
  unsubscribe(destination) {
    const subscription = this.subscriptions.get(destination);
    if (subscription) {
      subscription.unsubscribe();
      this.subscriptions.delete(destination);
    }
  }

  // Ngắt kết nối
  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
      this.stompClient = null;
      this.subscriptions.clear();
      console.log('WebSocket đã ngắt kết nối');
    }
  }
}

// Sử dụng singleton pattern
const webSocketService = new WebSocketService();
export default webSocketService;