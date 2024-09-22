document.addEventListener('DOMContentLoaded', () => {
   const WebSocketManager = {
      stompClient: null,

      connect() {
         if (typeof StompJs === 'undefined') {
            console.error('STOMP.js is not loaded');
            return;
         }

         if (sessionStorage.getItem('isConnected')) {
            console.log('WebSocket already connected in this session.');
            return;
         }

         const socket = new SockJS('/ws');
         this.stompClient = new StompJs.Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
         });

         this.stompClient.onConnect = frame => {
            console.log('Connected: ' + frame);
            sessionStorage.setItem('isConnected', 'true');
         };

         this.stompClient.onDisconnect = () => {
            console.log('Disconnected');
            sessionStorage.removeItem('isConnected');
         };

         this.stompClient.onStompError = frame => {
            console.error('STOMP error:', frame);
            sessionStorage.removeItem('isConnected');
            this.retryConnect();
         };

         this.stompClient.activate();
      },

      retryConnect() {
         setTimeout(() => {
            if (!sessionStorage.getItem('isConnected')) {
               console.log('Retrying WebSocket connection...');
               this.connect();
            }
         }, 5000);
      },

      disconnect() {
         if (sessionStorage.getItem('isConnected')) {
            sessionStorage.removeItem('isConnected');
            if (this.stompClient) {
               this.stompClient.deactivate();
            }
            console.log('WebSocket disconnected');
         } else {
            console.log('No active WebSocket connection.');
         }
      },

      initialize() {
         if (!sessionStorage.getItem('isConnected')) {
            this.connect();
         } else {
            console.log('WebSocket already connected in this session.');
         }
      },
   };

   WebSocketManager.initialize();

   const observer = new MutationObserver((mutations, obs) => {
      const logoutButton = document.getElementById('logout');
      if (logoutButton) {
         logoutButton.addEventListener('click', () => WebSocketManager.disconnect());
         console.log('Logout button found and event listener added.');
         obs.disconnect();
      }
   });

   observer.observe(document.body, {
      childList: true,
      subtree: true,
   });
});
