import React, { useState, useRef, use, useEffect } from "react";
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function App() {
  const [token, setToken] = useState("");
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const stompClientRef = useRef(null); // dùng ref để lưu client
  const subscriptionRef = useRef(null);

  const login = () => {
    fetch("http://localhost:8080/login?username=admin&password=b20af406-9d85-42d2-a78a-4b18d08ab873", {
      method: "POST"
    })
    .then(res => res.text())
    .then(token => {
      setToken(token);
      connectWs(token);
    });
  };

  const connectWs = (token) => {
    const stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      debug: (str) => console.log('[stompjs]', str),
      reconnectDelay: 5000,
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      onConnect: () => {
        console.log('✅ Connected to WebSocket');
        subscriptionRef.current = stompClient.subscribe('/topic/notification', (message) => {
          console.log('📩 Received message:', message);
        });
      },
    });

    stompClient.activate();
    stompClientRef.current = stompClient;
  };

  const sendMessage = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault(); // Ngăn chặn xuống dòng (nếu textarea)
      console.log('User nhấn Enter với nội dung:', input);
      // Gọi hàm gửi message, hoặc xử lý logic ở đây
      sendMessage(input);
      setInput('');
      const stompClient = stompClientRef.current;
      if (stompClient && input.trim() !== "") {
        const message = {
          sender: "ReactClient",
          content: input,
        };
        console.log("🚀 Sending:", message);
        stompClient.publish({
          destination: "/app/chat.send",
          body: JSON.stringify(message),
        });
        setInput("");
      }
    }
  };

  useEffect(() => {
    login();
  }, []);

  return (
    <div style={{ padding: 20 }}>
      {!token ? (
        <button onClick={login}>Login</button>
      ) : (
        <>
          <h3>Token:</h3>
          <p style={{ wordBreak: "break-word" }}>{token}</p>
          <h2>Chat Room</h2>
          <div>
            {messages.map((m, i) => (
              <div key={i}>
                <b>{m.sender}:</b> {m.content} 
              </div>
            ))}
          </div>
          <input value={input} onChange={(e) => setInput(e.target.value)} onKeyDown={sendMessage} />
          {/* <button onClick={sendMessage}>Send</button> */}
        </>
      )}
    </div>
  );
}

export default App;
