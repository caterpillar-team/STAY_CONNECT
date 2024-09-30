const WebSocketManager = {
   socket: null,
   stompClient: null,

   connect() {
      if (this.stompClient && this.stompClient.connected) {
         return;
      }

      this.socket = new SockJS('/ws');
      this.stompClient = new StompJs.Client({
         webSocketFactory: () => this.socket,
         reconnectDelay: 5000,
         heartbeatIncoming: 4000,
         heartbeatOutgoing: 4000,

         onConnect: frame => {
            console.log('Connected: ' + frame);
            this.stompClient.subscribe('/inquiry/messages', function (messageOutput) {
               showMessage(messageOutput.body);
            });
         },

         onStompError: frame => {
            console.log('STOMP: ' + frame);
         },
      });

      this.stompClient.activate();
   },

   publish(destination, message) {
      if (this.stompClient && this.stompClient.connected) {
         this.stompClient.publish({
            destination: destination,
            body: message,
         });
      }
   },
};

window.onload = function () {
   WebSocketManager.connect();

   const chatInput = document.getElementById('chatInput');
   const sendButton = document.getElementById('sendButton');

   // Enter 키로 메시지 전송
   chatInput.addEventListener('keydown', function (event) {
      if (event.key === 'Enter' && chatInput.value.trim() !== '') {
         sendMessage();
      }
   });

   // Send 버튼 클릭 시 메시지 전송
   sendButton.addEventListener('click', function () {
      sendMessage();
   });
};

// 메시지 전송 함수
function sendMessage() {
   const chatInput = document.getElementById('chatInput');
   const message = chatInput.value.trim();

   if (message !== '') {
      WebSocketManager.publish('/app/sendMessage', message);
      chatInput.value = ''; // 전송 후 입력 필드 비우기
   }
}

// 받은 메시지 표시 함수
function showMessage(message) {
   const chatMessages = document.getElementById('chatMessages');
   const messageElement = document.createElement('div');
   messageElement.innerText = message;
   chatMessages.appendChild(messageElement);
}
