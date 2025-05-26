import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// 👇 KHÔNG dùng plugin globals nào cả
export default defineConfig({
  plugins: [react()],
  define: {
    global: 'globalThis', // 👈 Dòng fix lỗi "global is not defined"
  },
  optimizeDeps: {
    include: ['@stomp/stompjs', 'sockjs-client']
  }
})
